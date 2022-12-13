package socket;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args)throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        InetAddress host = InetAddress.getLocalHost();
        final File[] fileToSend=new File[1];
        JFrame fenetre=new JFrame("Client");
        fenetre.setTitle("TRASFERT DU FICIER EN JAVA");
        fenetre.setSize(450, 450);
        fenetre.setLayout(new BoxLayout(fenetre.getContentPane(),BoxLayout.Y_AXIS));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label=new JLabel("FILE");
        JLabel label2=new JLabel("CHOOSE FILE");
        JPanel panel=new JPanel();
        JButton button=new JButton("ENVOYER");
        JButton button2=new JButton("VOS FICHIER");
        panel.add(button2);
        panel.add(button);

        button2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser=new JFileChooser();
                chooser.setDialogTitle("choose file to send");
                if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    fileToSend[0]=chooser.getSelectedFile();
                    //label2.setText("the file you want send is"+ "  "+  fileToSend[0].getName());
                }
            }
        });
        
        button.addActionListener(new ActionListener(){
            public  void actionPerformed(ActionEvent e) {
                if(fileToSend[0]==null){
                    label.setText("PLEASE CHOOSE A FILE FIRST");
                }else{
                    try{
                    FileInputStream fileInputStream=new FileInputStream(fileToSend[0].getAbsolutePath());
                    Socket socket=new Socket(host,8070);

                    DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());

                    String filename=fileToSend[0].getName();
                    byte[] filenamebytes=filename.getBytes();

                    byte[] filecontentBytes=new byte[(int)fileToSend[0].length()];
                    fileInputStream.read(filecontentBytes);

                    dataOutputStream.writeInt(filenamebytes.length);
                    dataOutputStream.write(filenamebytes);

                    dataOutputStream.writeInt(filecontentBytes.length);
                    dataOutputStream.write(filecontentBytes);
                    }catch(IOException ev){
                        ev.printStackTrace();
                    }
                }
            }
        });
        fenetre.add(label);
        fenetre.add(label2);
        fenetre.add(panel);
        fenetre.setVisible(true);
    }

}