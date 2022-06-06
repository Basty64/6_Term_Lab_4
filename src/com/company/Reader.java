package com.company;

import com.company.bdclass.*;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader { //здесь мы просто заполняем табличку данными


    static ArrayList<Reactor> Re = new ArrayList<>();

    public static ArrayList<Reactor> Read(File f) throws FileNotFoundException, ParseException {

        if (f.getPath().endsWith(".json")) {
            try {
                Re = Parser.JSON(f.getPath());
            } catch (IOException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Re;
    }

    public static void ForCompanies(JTable t, ArrayList<Companies> companies) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        companies.forEach(a -> model.addRow(new Object[]{a.getName(), a.getFuelConsuption()}));
    }

    public static void ForCountries(JTable t, ArrayList<Countries> countries) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        countries.forEach(a -> model.addRow(new Object[]{a.getName(), a.getFuelConsuption()}));
    }

    public static void ForRegions(JTable t, ArrayList<Regions> regions) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        regions.forEach(a -> model.addRow(new Object[]{a.getName(), a.getFuelConsuption()}));
    }
    public static void Tree(JTree t, ArrayList<Reactor> reactors){
        DefaultTreeModel tr = (DefaultTreeModel)t.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)t.getSelectionPath().getLastPathComponent();
        DefaultMutableTreeNode sourse = new DefaultMutableTreeNode(reactors.get(0).getSource());
        root.add(sourse);
        for (int i=0;i<34;i++){
            DefaultMutableTreeNode y = new DefaultMutableTreeNode(reactors.get(i).getName());
            sourse.add(y);
            String str[] = {"Class: "+reactors.get(i).getName(),
                    "Burnup: "+Double.toString(reactors.get(i).getBurnup()),
                    "Kpd: "+Double.toString(reactors.get(i).getKpd()),
                    "Enrichment: "+Double.toString(reactors.get(i).getEnrichment()),
                    "Termal capacity: "+Double.toString(reactors.get(i).getTermal_capacity()),
                    "Electrical capacity: "+Double.toString(reactors.get(i).getElectrical_capacity()),
                    "Life time: "+Double.toString(reactors.get(i).getLife_time()),
                    "First load: "+Double.toString(reactors.get(i).getFirst_load())};
            for (int j=0;j<8;j++){
                y.add(new DefaultMutableTreeNode(str[j]));
            }
        }
        tr.reload();}
    public static void Tree2(JTree t, ArrayList<Regions> Regions, ArrayList<Countries> Countries, ArrayList<Companies> Companies,
                             ArrayList<Sites> Sites, ArrayList<Units> Units) {
        DefaultTreeModel tr = (DefaultTreeModel) t.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) t.getSelectionPath().getLastPathComponent();
        Regions.forEach(a -> {
            DefaultMutableTreeNode x = new DefaultMutableTreeNode(a.getName());
            root.add(x);
            Countries.forEach(b -> {
                if (a.getId() == b.getRegion()) {
                    DefaultMutableTreeNode y = new DefaultMutableTreeNode(b.getName());
                    x.add(y);
                    Companies.forEach(c -> {
                        if (b.getId() == c.getCountry()) {
                            DefaultMutableTreeNode z = new DefaultMutableTreeNode(c.getName());
                            y.add(z);
                            Sites.forEach(d -> {
                                if (c.getId() == d.getCompany()) {
                                    d.setFuel((int) d.getFuel());

                                    DefaultMutableTreeNode i = new DefaultMutableTreeNode(d.getName() + "  ");
                                    z.add(i);

                                    Units.forEach(e -> {
                                        if (d.getId() == e.getSite()) {
                                            DefaultMutableTreeNode j = new DefaultMutableTreeNode(e.getName());
                                            i.add(j);
                                            d.setFuel((e.getFuel_consumption()));

                                        }
                                    });
                                    c.setFuelConsuption(d.getFuel());
                                }
                            });
                            b.setFuelConsuption(c.getFuelConsuption());
                        }
                    });
                    a.setFuelConsuption(b.getFuelConsuption());
                }
            });
        });
        tr.reload();

    }
    public static void DataBase(DBBuilder builder) throws SQLException {
        DBProvider provider = new DBProvider(builder);
        provider.connect();
        provider.getAll("regions", Re);
        provider.getAll("countries", Re);
        provider.getAll("sites", Re);
        provider.getAll("companies", Re);
        provider.getAll("units", Re);
        provider.close();
    }

}

