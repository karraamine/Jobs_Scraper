package model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import DB.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Piecharts extends JPanel {

    private Connection con;

    public Piecharts() {
        // SwingUtilities.invokeLater(Piecharts::new);
        // setTitle("Offers Statistics by Sector and City");
        // setSize(1200, 1200);
        // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(500, 1200));
        chartPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Create and add Sector Pie Chart
        JFreeChart sectorPieChart = createSectorPieChart();
        ChartPanel sectorChartPanel = new ChartPanel(sectorPieChart);
        sectorChartPanel.setMaximumSize(new Dimension(500, 1000));
        sectorChartPanel.setPreferredSize(new Dimension(500, 700));
        sectorChartPanel.setBorder(new EmptyBorder(0, 5, 20, 5));
        chartPanel.add(sectorChartPanel);

        // Create and add City Pie Chart
        JFreeChart cityPieChart = createCityPieChart();
        ChartPanel cityChartPanel = new ChartPanel(cityPieChart);
        cityChartPanel.setMaximumSize(new Dimension(600, 450));  // Adjusted width
        cityChartPanel.setPreferredSize(new Dimension(500, 450));
        cityChartPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        chartPanel.add(cityChartPanel);

        // scrollPane.setViewportView(chartPanel);
        add(chartPanel, BorderLayout.CENTER);

        Piecharts.this.setVisible(true);
    }

    private JFreeChart createSectorPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<String> sectors = getSectorList();

        for (String sector : sectors) {
            int offerCount = getOfferCountBySector(sector);
            dataset.setValue(sector, offerCount);
        }

        JFreeChart pieChart = ChartFactory.createPieChart("Percentage of Offers by Sector", dataset, true, true, false);
        configurePieChart(pieChart);

        return pieChart;
    }

    private JFreeChart createCityPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<String> ContractTypes = getContractTypeList();

        for (String ContractType : ContractTypes) {
            int offerCount = getOfferCountByContractType(ContractType);
            dataset.setValue(ContractType, offerCount);
        }

        JFreeChart pieChart = ChartFactory.createPieChart("Percentage of Offers by Contract Type", dataset, true, true, false);
        configurePieChart(pieChart);

        return pieChart;
    }

    private void configurePieChart(JFreeChart pieChart) {
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Other", new Color(192, 192, 192));
        plot.setExplodePercent("Other", 0.2);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setLabelGenerator(null);
        plot.setSectionOutlinesVisible(false);
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
    }

    private List<String> getSectorList() {
        List<String> sectors = new ArrayList<>();
        establishDatabaseConnection();

        try {
            String query = "SELECT DISTINCT sectors FROM Offre WHERE SiteName='rekrute.com' LIMIT 20";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        sectors.add(resultSet.getString("sectors"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return sectors;
    }

    private List<String> getContractTypeList() {
        List<String> cities = new ArrayList<>();
        establishDatabaseConnection();

        try {
            String query = "SELECT DISTINCT ContractType FROM Offre WHERE SiteName='rekrute.com' LIMIT 20";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        cities.add(resultSet.getString("ContractType"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }

        return cities;
    }

    private int getOfferCountByContractType(String ContractType) {
        int count = 0;
        establishDatabaseConnection();

        try {
            String query = "SELECT COUNT(*) AS count FROM Offre WHERE ContractType = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, ContractType);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt("count");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            closeDatabaseConnection();
        }
        return count;
    }

    private int getOfferCountBySector(String sector) {
        int count = 0;
        establishDatabaseConnection();

        try {
            String query = "SELECT COUNT(*) AS count FROM Offre WHERE sectors = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, sector);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt("count");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            closeDatabaseConnection();
        }

        return count;
    }

    private void establishDatabaseConnection() {
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeDatabaseConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(Piecharts::new);
    // }
}

