import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Font;

public class NumberDetect extends JFrame {

	private JPanel contentPane;
	private DrawPanel drawpanel;
	private JLabel lblResult0;
	private Network NeuroNet;
	int factor = 4;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NumberDetect frame = new NumberDetect();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NumberDetect() {
		setResizable(false);
		ArrayList<ArrayList<Double>> loadWeights = new ArrayList<ArrayList<Double>>();
		
		try
	    {
	     
	      FileInputStream fin = new FileInputStream("Weights.txt");
	      DataInputStream din = new DataInputStream(fin);
	      for (int i = 0; i < 28*28; i++)
	      {
	    	  loadWeights.add(new ArrayList<Double>());
	    	  for (int j = 0; j < 10; j++)
	    		  loadWeights.get(i).add(din.readDouble());
	    	  
	      }
	      din.close();	       
	    }
	    catch(IOException e)
	    {
	      System.out.println("IOException : " + e);
	    }
	  
		NeuroNet = new Network(28*28,10,0.05);
		NeuroNet.setWeights(loadWeights);
		
		setTitle("NumberDetect");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 162);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		drawpanel = new DrawPanel();
		drawpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		drawpanel.setBounds(10, 10, 112, 112);
		contentPane.add(drawpanel);
		
		JLabel label_0 = new JLabel("0");
		label_0.setBounds(132, 78, 16, 14);
		contentPane.add(label_0);
		
		JLabel label_1 = new JLabel("1");
		label_1.setBounds(158, 78, 23, 14);
		contentPane.add(label_1);
		
		JLabel lblResult1 = new JLabel("");
		lblResult1.setBounds(158, 99, 23, 23);
		contentPane.add(lblResult1);
		
		JLabel label_2 = new JLabel("2");
		label_2.setBounds(183, 78, 23, 14);
		contentPane.add(label_2);
		
		JLabel lblResult2 = new JLabel("");
		lblResult2.setBounds(183, 99, 23, 23);
		contentPane.add(lblResult2);
		
		JLabel label_3 = new JLabel("3");
		label_3.setBounds(207, 78, 23, 14);
		contentPane.add(label_3);
		
		JLabel lblResult3 = new JLabel("");
		lblResult3.setBounds(207, 99, 23, 23);
		contentPane.add(lblResult3);
		
		JLabel label_4 = new JLabel("4");
		label_4.setBounds(232, 78, 23, 14);
		contentPane.add(label_4);
		
		JLabel lblResult4 = new JLabel("");
		lblResult4.setBounds(232, 99, 23, 23);
		contentPane.add(lblResult4);
		
		JLabel label_5 = new JLabel("5");
		label_5.setBounds(256, 78, 23, 14);
		contentPane.add(label_5);
		
		JLabel lblResult5 = new JLabel("");
		lblResult5.setBounds(256, 99, 23, 23);
		contentPane.add(lblResult5);
		
		JLabel label_6 = new JLabel("6");
		label_6.setBounds(280, 78, 23, 14);
		contentPane.add(label_6);
		
		JLabel lblResult6 = new JLabel("");
		lblResult6.setBounds(280, 99, 23, 23);
		contentPane.add(lblResult6);
		
		JLabel label_7 = new JLabel("7");
		label_7.setBounds(305, 78, 23, 14);
		contentPane.add(label_7);
		
		JLabel lblResult7 = new JLabel("");
		lblResult7.setBounds(305, 99, 23, 23);
		contentPane.add(lblResult7);
		
		JLabel label_8 = new JLabel("8");
		label_8.setBounds(328, 78, 23, 14);
		contentPane.add(label_8);
		
		JLabel lblResult8 = new JLabel("");
		lblResult8.setBounds(328, 99, 23, 23);
		contentPane.add(lblResult8);
		
		JLabel label_9 = new JLabel("9");
		label_9.setBounds(354, 78, 23, 14);
		contentPane.add(label_9);
		
		JLabel lblResult9 = new JLabel("");
		lblResult9.setBounds(354, 99, 23, 23);
		contentPane.add(lblResult9);
		
		
		JLabel lblDetect = new JLabel();
		lblDetect.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblDetect.setBounds(256, 10, 57, 57);
		contentPane.add(lblDetect);

		//Color panel white
		this.clearCanvas();
		
		for (int j = 0; j < 28*28; j++)
		{
			int[] data = new int[1]; 
			data[0] = 255;
			
			for (int k = 0; k < factor; k++)
			{
				for (int l = 0; l < factor; l++)
					drawpanel.img.getRaster().setPixel(factor*(j/28)+k, factor*(j%28)+l, data);
			}
		}
		
		drawpanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//Break if not on Panel
				int rescaledX = e.getX()/factor, rescaledY = e.getY()/factor;
				if (rescaledX < 0 || rescaledX > 27 || rescaledY < 0 || rescaledY > 27)
					return;
				
				int [][] scaledimg = new int [28][28];
				
				//Draw Taxicab-Brush
				scaledimg[rescaledX]				[rescaledY] 				= 	255;
				scaledimg[rescaledX]				[Math.max(rescaledY-1, 0)]	=	32;
				scaledimg[Math.max(rescaledX-1, 0)]	[rescaledY]					=	32;
				scaledimg[rescaledX]				[Math.min(rescaledY+1,27)]	=	32;
				scaledimg[Math.min(rescaledX+1,27)]	[rescaledY]					=	32;
				
				scaledimg[Math.max(rescaledX-1, 0)]	[Math.max(rescaledY-1, 0)]	=	32;
				scaledimg[Math.max(rescaledX-1, 0)]	[Math.min(rescaledY+1,27)]	=	32;
				scaledimg[Math.min(rescaledX+1,27)]	[Math.min(rescaledY+1,27)]	=	32;
				scaledimg[Math.min(rescaledX+1,27)]	[Math.max(rescaledY-1, 0)]	=	32;
				
				//draw the panel, subtracting the created scaled image (scaling the oldimg down in the process)
				for (int j = 0; j < 28*28; j++)
				{
					int[] data = new int[1], olddata = drawpanel.img.getRaster().getPixel(factor*(j/28), factor*(j%28), (int[])null); 
					data[0] = Math.max(0, olddata[0]-scaledimg[j/28][j%28]);
					
					for (int k = 0; k < factor; k++)
					{
						for (int l = 0; l < factor; l++)
							drawpanel.img.getRaster().setPixel(factor*(j/28)+k, factor*(j%28)+l, data);
					}
				}
				contentPane.validate();
				contentPane.repaint();
			}
		});
		//-------------------------------------
		drawpanel.setBounds(10, 10, 112, 112);
		contentPane.add(drawpanel);
		
		JButton btnDetect = new JButton("Detect\r\n");
		btnDetect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get rescaled data from canvas
				ArrayList<Double> inputLayer = new ArrayList<Double>();
				
				for (int i = 0; i < 28*28; i++)
					inputLayer.add(Network.Sigmoid((double)(255-
							//drawpanel.img.getRaster().getPixel(factor*(i/28), factor*(i%28), (int[])null)[0])); 
							drawpanel.img.getRaster().getPixel(factor*(i/28), factor*(i%28), (int[])null)[0])));
			
			NeuroNet.setInputLayer(inputLayer);
			ArrayList<Double> OutputLayer = NeuroNet.EvaluateNetwork();
			
			//Print probabilities
			lblResult0.setText(Integer.toString((int)(OutputLayer.get(0)*100))+"%");
			lblResult1.setText(Integer.toString((int)(OutputLayer.get(1)*100))+"%");
			lblResult2.setText(Integer.toString((int)(OutputLayer.get(2)*100))+"%");
			lblResult3.setText(Integer.toString((int)(OutputLayer.get(3)*100))+"%");
			lblResult4.setText(Integer.toString((int)(OutputLayer.get(4)*100))+"%");
			lblResult5.setText(Integer.toString((int)(OutputLayer.get(5)*100))+"%");
			lblResult6.setText(Integer.toString((int)(OutputLayer.get(6)*100))+"%");
			lblResult7.setText(Integer.toString((int)(OutputLayer.get(7)*100))+"%");
			lblResult8.setText(Integer.toString((int)(OutputLayer.get(8)*100))+"%");
			lblResult9.setText(Integer.toString((int)(OutputLayer.get(9)*100))+"%");
			
			//Get best guess
			int bestguess = 0;
			for (int j = 1; j < 10; j++)
			{
				if (OutputLayer.get(j) > OutputLayer.get(bestguess))
					bestguess = j;
			}
			
			lblDetect.setText(Integer.toString(bestguess));
			
		}});
		btnDetect.setBounds(132, 10, 112, 23);
		contentPane.add(btnDetect);
		
		lblResult0 = new JLabel("");
		lblResult0.setBounds(132, 99, 23, 23);
		contentPane.add(lblResult0);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearCanvas();
				drawpanel.validate();
				drawpanel.repaint();
			}
		});
		btnClear.setBounds(132, 44, 112, 23);
		contentPane.add(btnClear);


	}
	
	
	private void clearCanvas()
	{
		for (int j = 0; j < 28*28; j++)
		{
			int[] data = new int[1]; 
			data[0] = 255;
			
			for (int k = 0; k < factor; k++)
			{
				for (int l = 0; l < factor; l++)
					drawpanel.img.getRaster().setPixel(factor*(j/28)+k, factor*(j%28)+l, data);
			}
		}
		
	}
}
