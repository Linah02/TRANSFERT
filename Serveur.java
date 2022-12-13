package socket;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.io.FileOutputStream;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import socket.Fichier;
public class Serveur{
    
    static ArrayList<Fichier> File=new ArrayList<>();
    public static void main(String[] args)throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
    int fileId=0;
    JFrame fenetre=new JFrame("Serveur");
    fenetre.setTitle("TRASFERT DU FICIER EN JAVA");
    fenetre.setSize(450, 450);
    fenetre.setLayout(new BoxLayout(fenetre.getContentPane(),BoxLayout.Y_AXIS));
    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel label=new JLabel("Fichier Recues");
    JScrollPane jScrollPane=new JScrollPane();
    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    JPanel panel=new JPanel();
   
    fenetre.add(label);
    fenetre.add(jScrollPane);
    fenetre.setVisible(true);
        ServerSocket socket=new ServerSocket(5000);
        while(true){
            try {
                Socket socket2=socket.accept();
                DataInputStream dataInputStream=new DataInputStream(socket2.getInputStream());
                int  filenamelength=dataInputStream.readInt();
                if(filenamelength>0){
                    byte[] filenamebytes=new byte[filenamelength];
                    dataInputStream.readFully(filenamebytes,0,filenamebytes.length);
                    String filename=new String(filenamebytes);
                    int filecontentlength=dataInputStream.readInt();
                    if(filecontentlength>0){
                        byte[] filecontentBytes=new byte[filecontentlength];
                        dataInputStream.readFully(filecontentBytes, 0, filecontentlength);
                        JPanel paneau=new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                        JLabel label2=new JLabel(filename);
                        label2.setFont(new Font("Arial",Font.BOLD,20));
                        label2.setBorder(new EmptyBorder(10, 0, 10, 0));
                        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
                        if(getFileExtensions(filename).equalsIgnoreCase("jpg")){
                            paneau.setName(String.valueOf(fileId));
                            paneau.addMouseListener(getMyMouseListener());
                            paneau.add(label2);
                            jScrollPane.add(paneau);
                            fenetre.validate();
                            
                        }else{
                            paneau.setName(String.valueOf(fileId));
                            paneau.addMouseListener(getMyMouseListener());
                            paneau.add(label2);
                            jScrollPane.add(paneau);
                            fenetre.validate();
                        }
                    //myFiles.add(new MyFile(fileId, filename, filenamebytes, getFileExtensions(filename))); 
                    fileId++;
                }
            }
            } catch (Exception e) {  e.printStackTrace(); }
        }
    }
    public static MouseListener getMyMouseListener(){
        return new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                JPanel jPanel=(JPanel) e.getSource();
                int fileId=Integer.parseInt(jPanel.getName());
                for(File myFile:Fichier){
                    if (myFile.getId()==fileId) {
                        JFrame jF=createFrame(myFile.getName(),myFile.getData(),myFile.getFileExtensions());
                        jF.setVisible(true);
                    }
                }
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {            }
            public void mouseExited(MouseEvent e) {}
        };
    }
    public static String getFileExtensions(String files) {
        int i=files.lastIndexOf(".");
        if(i>0){
            return files.substring(i+1);
        }else{
            return "no extensions found";
        }
    }
    public static JFrame createFrame(String fichier,byte[] fileData,String filExtensions) {
        JFrame jFrame=new JFrame("withcode's file download");
        jFrame.setSize(400, 400);

        JPanel jPanel=new JPanel();
        JLabel jLabel=new JLabel("file download");
        JLabel labb=new JLabel("are you sure you want to dowload"+fichier);
        JButton butt=new JButton("yes");
        JButton butt1Button=new JButton("no");
        JLabel jLabel2=new JLabel();
        JPanel jPanel2=new JPanel();

        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setFont(new Font("Arial",Font.BOLD,25));
        jLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
        labb.setBorder(new EmptyBorder(20, 0, 10, 0));
        labb.setAlignmentX(Component.CENTER_ALIGNMENT);
        butt.setPreferredSize(new Dimension(150,20));
        butt1Button.setPreferredSize(new Dimension(150,20));    
        jLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel2.setBorder(new EmptyBorder(20, 0, 10, 0));
        jPanel2.add(butt1Button);
        jPanel2.add(butt);

        if(filExtensions.equalsIgnoreCase("txt")){
            jLabel2.setText("<html>"+new String(fileData)+"</html>");
        }
        else{
            jLabel2.setIcon(new ImageIcon(fileData));
        }
        butt.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            File fileToDownload= new File(fichier);
                try {
                    FileOutputStream fileOutputStream=new FileOutputStream(fileToDownload);
                    fileOutputStream.write(fileData);
                    fileOutputStream.close();
                    jFrame.dispose();
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    exception.printStackTrace();
                    // TODO: handle exception
                }
            }
        });
        butt1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
        jPanel.add(jLabel);
        jPanel.add(labb);
        jPanel.add(jLabel2);
        jPanel.add(jPanel2);
        jFrame.add(jPanel);
        return jFrame;

    }
}