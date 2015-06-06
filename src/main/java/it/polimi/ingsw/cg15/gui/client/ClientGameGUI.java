package it.polimi.ingsw.cg15.gui.client;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ClientGameGUI implements Runnable{
    
    
	int[][] board = new int[23][15];
	JPanel map;
	private JFrame frame;
	SpotlightLayerUI spotlightLayerUI;
	JLayer<JPanel> jlayerSpot;
	boolean spotlightEnable = false;

	@Override
	public void run() {
        frame.setLocationRelativeTo(null);  
		//frame.setVisible(true);
	}

	
	
	public	ClientGameGUI(){
		prepareFrame();
	      System.out.println("GUI creato");

	}
	public void prepareFrame(){

		frame = new JFrame("Escape From Aliens in Outer Space");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		frame.setResizable(false);

		
		JPanel panel = new JPanel(true);
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BorderLayout());



		map = new MapPanel(true,board);
		frame.setSize(1280,720);


		JPanel sidepanel = new SidePanel();



		frame.add(sidepanel,BorderLayout.EAST);		
		frame.add(map, BorderLayout.WEST);
		frame.add(panel);
		
		
		spotlightLayerUI = new SpotlightLayerUI();
		jlayerSpot = new JLayer<JPanel>(map, spotlightLayerUI);
		frame.add(jlayerSpot);
		
		
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem loadMenu = new JMenuItem("Load");
        JMenu debugMenu = new JMenu("Debug");
        JMenuItem spotDebugMenu = new JMenuItem("Spotlight");
        
        spotDebugMenu.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(spotlightEnable==true){
                    spotlightLayerUI.enableSpot();
                    spotlightEnable=false;
                }
                else{
                    spotlightLayerUI.disableSpot();
                    spotlightEnable=true;
                    
                }
                
            }
        });

        loadMenu.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInputStream fin = null;
                
                String mapName = JOptionPane.showInputDialog(frame, "Insert Map name to load");
                if(mapName==""){
                    mapName="map";
                }
                File file = new File("maps/"+mapName+".txt");
                try {
                    fin=new FileInputStream(file);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
                
                
         
                String line=null;
                        
                try {
                    line = reader.readLine();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                while(line != null){
                   // System.out.println(line);
                    String[] splitted = line.split(",");
                  
                  
                      board[Integer.valueOf(splitted[1])-1][Integer.valueOf(splitted[0])-1]=Integer.valueOf(splitted[2]);
                 
                  
                    
                    try {
                        line = reader.readLine();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }   
                map.repaint();
            }
            
            
        });
        
        
        saveMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                FileOutputStream fop = null;
                File file;
                String content="";//="version,0.1"+"\n"+"mapname,prova"+"\n"+
                                //"row,column,sector value,\n";
                String mapName = JOptionPane.showInputDialog(frame, "Insert Map Name to save");
                if(mapName==""){
                    mapName="map";
                }
                try {
         
                    file = new File("maps/"+mapName+".txt");
                    fop = new FileOutputStream(file);
         
                    // if file doesnt exists, then create it
                    if (!file.exists()) {
                        file.createNewFile();
                    }
         
                    // get the content in bytes
                    for(int i=0;i<15;i++){
                        for(int j=0; j<23; j++){
                            content=content+(i+1)+","+(j+1)+","+board[j][i]+"\n"; 
                        }
                    }
                    byte[] contentInBytes = content.getBytes();
         
                    fop.write(contentInBytes);
                    fop.flush();
                    fop.close();
         
                    System.out.println("Done");
         
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fop != null) {
                            fop.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        debugMenu.add(spotDebugMenu);
        menu.add(saveMenu);
        menu.add(loadMenu);
		menuBar.add(menu);
		menuBar.add(debugMenu);
		frame.setJMenuBar(menuBar);

		
		

		frame.pack();
		GraphicsDevice device = GraphicsEnvironment
		        .getLocalGraphicsEnvironment().getScreenDevices()[0];
       //device.setFullScreenWindow(frame);
		


	}

	public void showGUI(){
	    this.frame.setVisible(true);
	}
	
	
}