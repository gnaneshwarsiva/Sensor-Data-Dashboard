
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class SensorDashboard {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SensorDashboard().createDashboard());
    }

    private void createDashboard() {
        JFrame frame = new JFrame("IoT Sensor Dashboard - January 2025");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        List<SensorData> data = loadCSV("sensor_data_jan2025.csv");

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("📈 Line Chart", createLineChart(data));
        tabs.addTab("📊 Bar Chart", createBarChart(data));
        tabs.addTab("🥧 Pie Chart", createPieChart(data));
        tabs.addTab("📋 Analysis", createAnalysisPanel(data));

        frame.add(tabs);
        frame.setVisible(true);
    }

    private List<SensorData> loadCSV(String fileName) {
        List<SensorData> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 3) continue;
                String sensor = tokens[0];
                double value = Double.parseDouble(tokens[1]);
                long timestamp = Long.parseLong(tokens[2]);
                data.add(new SensorData(sensor, value, timestamp));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private JPanel createLineChart(List<SensorData> data) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Map<String, TimeSeries> seriesMap = new HashMap<>();
        for (SensorData sd : data) {
            seriesMap.putIfAbsent(sd.sensor, new TimeSeries(sd.sensor));
            seriesMap.get(sd.sensor).addOrUpdate(new Second(new Date(sd.timestamp)), sd.value);
        }
        for (TimeSeries ts : seriesMap.values()) dataset.addSeries(ts);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Sensor Values Over Time","Time","Value",dataset);
        return new ChartPanel(chart);
    }

    private JPanel createBarChart(List<SensorData> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<Double>> valuesMap = new HashMap<>();
        for (SensorData sd : data) {
            valuesMap.computeIfAbsent(sd.sensor, k -> new ArrayList<>()).add(sd.value);
        }
        for (Map.Entry<String, List<Double>> entry : valuesMap.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0);
            dataset.addValue(avg, "Average", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart("Average Sensor Values","Sensor","Average Value",dataset);
        return new ChartPanel(chart);
    }

    private JPanel createPieChart(List<SensorData> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, List<Double>> valuesMap = new HashMap<>();
        for (SensorData sd : data) valuesMap.computeIfAbsent(sd.sensor, k -> new ArrayList<>()).add(sd.value);
        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : valuesMap.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0);
            averages.put(entry.getKey(), avg);
        }
        for (Map.Entry<String, Double> entry : averages.entrySet()) dataset.setValue(entry.getKey(), entry.getValue());
        JFreeChart chart = ChartFactory.createPieChart("Sensor Value Distribution",dataset,true,true,false);
        return new ChartPanel(chart);
    }

    private JPanel createAnalysisPanel(List<SensorData> data) {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea(25, 60);
        textArea.setEditable(false);
        Map<String, List<Double>> valuesMap = new HashMap<>();
        for (SensorData sd : data) valuesMap.computeIfAbsent(sd.sensor, k -> new ArrayList<>()).add(sd.value);
        StringBuilder analysis = new StringBuilder("📊 Sensor Analysis for January 2025\n\n");
        for (Map.Entry<String, List<Double>> entry : valuesMap.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double min = entry.getValue().stream().mapToDouble(Double::doubleValue).min().orElse(0);
            double max = entry.getValue().stream().mapToDouble(Double::doubleValue).max().orElse(0);
            analysis.append("Sensor: ").append(entry.getKey()).append("\n")
                    .append("Average: ").append(String.format("%.2f", avg)).append("\n")
                    .append("Minimum: ").append(String.format("%.2f", min)).append("\n")
                    .append("Maximum: ").append(String.format("%.2f", max)).append("\n\n");
        }
        textArea.setText(analysis.toString());
        panel.add(new JScrollPane(textArea));
        return panel;
    }

    private static class SensorData {
        String sensor;
        double value;
        long timestamp;
        SensorData(String sensor, double value, long timestamp) {
            this.sensor = sensor; this.value = value; this.timestamp = timestamp;
        }
    }
}
