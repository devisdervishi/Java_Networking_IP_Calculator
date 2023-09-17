import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    JPanel panel1;
    JTextField ipField;
    JTextField subnMask;
    JLabel ipLabel;
    JLabel subnMaskLabel;
    JButton calcuate;
    JPanel panel2;
    JLabel broadcastIPLabel;
    JTextField broadCastIp;
    JLabel networkIdLabel;
    JTextField networkId;
    JLabel subnetMaskLabel;
    JTextField subnetMask;
    JLabel firstIpLabel;
    JTextField firstIp;
    JLabel lastIpLabel;
    JTextField lastIp;
    JLabel numOfHostsLabel;
    JTextField numOfHosts;
    JLabel ipClassLabel;
    JTextField ipClass;
    JTextArea errorField;
    public MainFrame(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(420,500);
        this.setLocationRelativeTo(null);
        createComponents();
        this.setVisible(true);

    }

    class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            errorField.setText("");
            String ipNumbers[]=ipField.getText().split("\\.");

            int subMask;
            if(ipNumbers.length!=4){
                errorField.setText("Gabim ne dhenien e ip");
                return;
            }
            try{
                subMask=Integer.parseInt(subnMask.getText());
            }catch (Exception ex){
                errorField.setText(errorField.getText()+"Gabim ne dhenien e subnet mask\n");
                return;
            }
            if (subMask<8||subMask>30){
                errorField.setText(errorField.getText()+"Gabim ne dhenien e subnet mask\n");
                return;
            }
            int ipNumbersDecimal[]=new int[ipNumbers.length];
            for (int i=0;i<ipNumbers.length;i++){
                try{
                    ipNumbersDecimal[i]=Integer.parseInt(ipNumbers[i]);
                    if(ipNumbersDecimal[i]<0||ipNumbersDecimal[i]>255){
                        errorField.setText(errorField.getText()+"Gabim ne dhenien e ip\n");
                        return;
                    }
                }catch (Exception ex){
                    errorField.setText(errorField.getText()+"Gabim ne dhenien e ip\n");
                    return;
                }
            }
            String ipNumbersBinary[]=new String[ipNumbersDecimal.length];
            for (int i=0;i<ipNumbersBinary.length;i++){
                ipNumbersBinary[i]=Integer.toBinaryString(ipNumbersDecimal[i]);
                while(ipNumbersBinary[i].length()!=8){
                    ipNumbersBinary[i]="0"+ipNumbersBinary[i];
                }
            }
            int bitetEngelur=subMask%8;
            int bitetEplota=subMask/8;
            String networkIdString="";
            for (int i=0;i<bitetEplota;i++){
                networkIdString+=ipNumbersDecimal[i]+".";
            }
            if(bitetEngelur!=0) {
                String temp = ipNumbersBinary[bitetEplota].substring(0, bitetEngelur);
                while (temp.length() != 8) {
                    temp += 0;
                }
                networkIdString+=Integer.parseInt(temp,2);
            }
           else networkIdString+="0";
            for (int i=bitetEplota+1;i<ipNumbersBinary.length;i++){
                networkIdString+=".0";
            }
            networkId.setText(networkIdString);

            String broadcastIdString="";
            for (int i=0;i<bitetEplota;i++){
                broadcastIdString+=ipNumbersDecimal[i]+".";
            }
            if(bitetEngelur!=0) {
                String temp = ipNumbersBinary[bitetEplota].substring(0, bitetEngelur);
                while (temp.length() != 8) {
                    temp += 1;
                }
                broadcastIdString+=Integer.parseInt(temp,2);
            }
            else broadcastIdString+="255";
            for (int i=bitetEplota+1;i<ipNumbersBinary.length;i++){
                broadcastIdString+=".255";
            }
            broadCastIp.setText(broadcastIdString);

            String subnetMaskString="";
            for (int i=0;i<bitetEplota;i++){
                subnetMaskString+="255.";
            }
            if(bitetEngelur!=0) {
                String temp = ipNumbersBinary[bitetEplota].substring(0, bitetEngelur);
                while (temp.length() != 8) {
                    temp += 0;
                }
                subnetMaskString+=Integer.parseInt(temp,2);
            }
            else subnetMaskString+="0";
            for (int i=bitetEplota+1;i<ipNumbersBinary.length;i++){
                subnetMaskString+=".0";
            }
            subnetMask.setText(subnetMaskString);

            String BroadcastIpNumbers[]=broadCastIp.getText().split("\\.");
            BroadcastIpNumbers[3]=""+(Integer.parseInt(BroadcastIpNumbers[3])-1);
            String lastIpString="";
            for (int i=0;i<BroadcastIpNumbers.length;i++){
                if (i==3){
                    lastIpString+=BroadcastIpNumbers[i];
                    break;
                }
                lastIpString+=BroadcastIpNumbers[i]+".";
            }
            lastIp.setText(lastIpString);

            String networkIdNumbers[]=networkId.getText().split("\\.");
            networkIdNumbers[3]=""+(Integer.parseInt(networkIdNumbers[3])+1);
            String firstIpString="";
            for (int i=0;i<networkIdNumbers.length;i++){
                if (i==3){
                    firstIpString+=networkIdNumbers[i];
                    break;
                }
                firstIpString+=networkIdNumbers[i]+".";
            }
            firstIp.setText(firstIpString);

            numOfHosts.setText(""+(int)(Math.pow(2,(32-subMask*1.0))-2));
            if(Integer.parseInt(ipNumbers[0])<=126){
                ipClass.setText("A");
            }
            else if(Integer.parseInt(ipNumbers[0])<=191){
                ipClass.setText("B");
            }
            else if(Integer.parseInt(ipNumbers[0])<=223){
                ipClass.setText("C");
            }
            else if(Integer.parseInt(ipNumbers[0])<=239){
                ipClass.setText("D");
            }
            else if(Integer.parseInt(ipNumbers[0])<=255){
                ipClass.setText("E");
            }
        }
    }

    public void createComponents(){
        panel1=new JPanel();
        ipLabel=new JLabel("IP/Sub.Mask");
        subnMaskLabel=new JLabel(" / ");
        ipField=new JTextField(15);
        subnMask=new JTextField(2);
        calcuate=new JButton("Calculate");
        Listener listener=new Listener();
        calcuate.addActionListener(listener);
        panel1.add(ipLabel);
        panel1.add(ipField);
        panel1.add(subnMaskLabel);
        panel1.add(subnMask);
        panel1.add(calcuate);
        panel2=new JPanel(new GridLayout(7,2));
        broadcastIPLabel = new JLabel("   Broadcast ID:");
        broadCastIp=new JTextField();
        broadCastIp.setEditable(false);
        networkIdLabel=new JLabel("   Network ID");
        networkId=new JTextField();
        networkId.setEditable(false);
        subnetMaskLabel=new JLabel("   Subnet Mask");
        subnetMask=new JTextField();
        subnetMask.setEditable(false);
        firstIpLabel=new JLabel("   First Ip");
        firstIp=new JTextField();
        firstIp.setEditable(false);
        lastIpLabel=new JLabel("   Last Ip");
        lastIp=new JTextField();
        lastIp.setEditable(false);
        numOfHostsLabel=new JLabel("   Number of hosts");
        numOfHosts=new JTextField();
        numOfHosts.setEditable(false);
        ipClassLabel=new JLabel("   IP Class");
        ipClass=new JTextField();
        ipClass.setEditable(false);
        panel2.add(ipClassLabel);
        panel2.add(ipClass);
        panel2.add(broadcastIPLabel);
        panel2.add(broadCastIp);
        panel2.add(networkIdLabel);
        panel2.add(networkId);
        panel2.add(subnetMaskLabel);
        panel2.add(subnetMask);
        panel2.add(firstIpLabel);
        panel2.add(firstIp);
        panel2.add(lastIpLabel);
        panel2.add(lastIp);
        panel2.add(numOfHostsLabel);
        panel2.add(numOfHosts);
        this.add(panel1,BorderLayout.NORTH);
        this.add(panel2,BorderLayout.CENTER);
        errorField=new JTextArea(2,50);
        Font font = new Font("Arial", Font.BOLD, 30);
        errorField.setFont(font);
        errorField.setForeground(Color.RED);
        this.add(errorField,BorderLayout.SOUTH);

    }
}
