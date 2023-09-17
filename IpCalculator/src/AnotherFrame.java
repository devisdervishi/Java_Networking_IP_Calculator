import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnotherFrame extends JFrame {

    JPanel panel1;
    JLabel ipLabel;
    JTextField ipField;
    JLabel subBitsLabel;
    JTextField subBits;
    JLabel subsToProduceLabel;
    JTextField subsToProduce;
    JLabel hostsPerSubsLabel;
    JTextField hostsPerSubs;
    JButton go;
    JPanel panel2;
    JScrollPane scrollPane;
    JPanel error;
    JTextArea errorMessage;

    public AnotherFrame(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(820,555);
        this.setLocationRelativeTo(null);
        createComponents();
        this.setVisible(true);
    }

    private void createComponents() {
        panel1=new JPanel();
        ipLabel=new JLabel("Ip: ");
        ipField=new JTextField(15);
        subBitsLabel=new JLabel(" / ");
        subBits=new JTextField(2);
        subsToProduceLabel =new JLabel("Nr subnetesh: ");
        subsToProduce=new JTextField(2);
        go=new JButton("Go");
        Listener listener=new Listener();
        go.addActionListener(listener);
        hostsPerSubsLabel=new JLabel("Hosts numbers");
        hostsPerSubs=new JTextField(15);
        panel1.add(ipLabel);
        panel1.add(ipField);
        panel1.add(subBitsLabel);
        panel1.add(subBits);
        panel1.add(subsToProduceLabel);
        panel1.add(subsToProduce);
        panel1.add(hostsPerSubsLabel);
        panel1.add(hostsPerSubs);
        panel1.add(go);
        error=new JPanel();
        errorMessage=new JTextArea(5,20);
        Font font = new Font("Arial", Font.BOLD, 30);
        errorMessage.setFont(font);
        errorMessage.setForeground(Color.RED);
        this.add(panel1,BorderLayout.NORTH);
        this.add(errorMessage,BorderLayout.SOUTH);


    }

    class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(scrollPane!=null||panel2!=null){
                removePanel(scrollPane,panel2);
            }
            panel2=new JPanel(new GridLayout(Integer.parseInt(subsToProduce.getText()),1));
             scrollPane=new JScrollPane(panel2);

            errorMessage.setText("");
            String ipNumbersDecimal[]=ipField.getText().split("\\.");
            String ipNumbersBinary[]=new String[ipNumbersDecimal.length];
            String hostsForEachSubnet[]= hostsPerSubs.getText().split(",");
            String fullBinaryIp="";
            for (int i = 0; i < ipNumbersDecimal.length; i++) {
                ipNumbersBinary[i]=decimalToBinary(Integer.parseInt(ipNumbersDecimal[i]),8);
                fullBinaryIp+=decimalToBinary(Integer.parseInt(ipNumbersDecimal[i]),8);
            }

            int subNetBits=Integer.parseInt(subBits.getText());
            int subnetsToProduce=Integer.parseInt(subsToProduce.getText());
            if(subnetsToProduce!=hostsForEachSubnet.length){
                if(scrollPane!=null||panel2!=null){
                    removePanel(scrollPane,panel2);
                }
                errorMessage.setText("Numri i subneteve nuk eshte i njejte me numrin e\nhosteve per çdo subnet");
                return;
            }
            int hostsForEachSubnetCreated[]=new int[subnetsToProduce];

            double bitsForSubnets=(Math.log(subnetsToProduce) / Math.log(2));

            if(bitsForSubnets%2!=0){
                bitsForSubnets=(int)bitsForSubnets+1;
            }


            int fullNumbers=(int)subNetBits/8;
            int leftBits=subNetBits%8;
            String fullBinaryIpTemp=fullBinaryIp.substring(0,fullNumbers*8);

            fullBinaryIpTemp+=ipNumbersBinary[fullNumbers].substring(0,leftBits);
            String fullBinaryIpTemp2=fullBinaryIpTemp;
            //bitet borxh qe po merren per gjenerimin e subnetit
            int numberOfBitsUsedToCreateSubnet=1;
            int arrayIndex=0;
            while (subnetsToProduce>=0&&numberOfBitsUsedToCreateSubnet<=bitsForSubnets){
                 fullBinaryIpTemp2=fullBinaryIpTemp;
                //numri qe formjne bitet qe po perdoren per gjenerimin e subnetit
                int numbersCreatedByBits=0;
                while (numbersCreatedByBits<Math.pow(2,numberOfBitsUsedToCreateSubnet)){

                    fullBinaryIpTemp2=fullBinaryIpTemp;
                    if(subnetsToProduce<=0){
                        addPanel2();
                        return;
                    }
                    fullBinaryIpTemp2+=decimalToBinary(numbersCreatedByBits,numberOfBitsUsedToCreateSubnet);

                    String networkId=fullBinaryIpTemp2;
                    String broadCastId=fullBinaryIpTemp2;
                    for(int i=fullBinaryIpTemp2.length();i<=32;i++){
                        networkId+="0";
                        broadCastId+="1";
                    }
                    String firstIp=networkId.substring(0,networkId.length()-2)+"1";

                    String lastIp=broadCastId.substring(0,broadCastId.length()-2)+"0";
                    JPanel temp=new JPanel(new GridLayout(4,2));
                    temp.setBorder(new EmptyBorder(10,10,10,10));
                    JLabel netIdLabel=new JLabel("   Network Id: ");
                    JTextField netId=new JTextField(binaryToDecimalIpProvider(networkId)+" / "+(subNetBits+numberOfBitsUsedToCreateSubnet));
                    JLabel firstIpLabel=new JLabel("   First Ip: ");
                    JTextField fIp=new JTextField(binaryToDecimalIpProvider(firstIp)+" / "+(subNetBits+numberOfBitsUsedToCreateSubnet));
                    JLabel lastIpLabel=new JLabel("   Last Ip: ");
                    JTextField lIp=new JTextField(binaryToDecimalIpProvider(lastIp)+" / "+(subNetBits+numberOfBitsUsedToCreateSubnet));
                    JLabel broadIdLabel=new JLabel("   BroadCast Id: ");
                    JTextField broadId=new JTextField(binaryToDecimalIpProvider(broadCastId)+" / "+(subNetBits+numberOfBitsUsedToCreateSubnet));

                    hostsForEachSubnetCreated[arrayIndex]=(int)Math.pow(2,(32-(subNetBits+numberOfBitsUsedToCreateSubnet)))-2;

                   if ( hostsForEachSubnetCreated[arrayIndex]<Integer.parseInt(hostsForEachSubnet[arrayIndex])){
                       if(scrollPane!=null||panel2!=null){
                           removePanel(scrollPane,panel2);
                       }
                       errorMessage.setText("Nuk mund te gjenerohen subnetet");
                       return;
                   }
                   arrayIndex++;

                    temp.add(netIdLabel);
                    temp.add(netId);
                    temp.add(firstIpLabel);
                    temp.add(fIp);
                    temp.add(lastIpLabel);
                    temp.add(lIp);
                    temp.add(broadIdLabel);
                    temp.add(broadId);
                    panel2.add(temp);
                    numbersCreatedByBits++;
                    subnetsToProduce--;

                }

                numberOfBitsUsedToCreateSubnet++;
            }


        }
    }
public void removePanel(JScrollPane scrollPane,JPanel panel){
        this.remove(panel);
        this.remove(scrollPane);
        this.revalidate();
        this.repaint();
}
public void addPanel2(){
        this.add(scrollPane,BorderLayout.CENTER);
        this.revalidate();
}
public  String decimalToBinary(int decimal,int bits){
        String binary=Integer.toBinaryString(decimal);
        String binaryWithBits="";
        for(int i=bits-binary.length();i>0;i--){
            binaryWithBits+="0";
        }
        binaryWithBits+=binary;
        return binaryWithBits;
}
public String binaryToDecimalIpProvider(String ip){
        int beginIndx=0;
        int endIndx=8;
        String decimalIp="";
    for (int i = 0; i < 4; i++) {
        decimalIp+=Integer.parseInt(ip.substring(beginIndx,endIndx),2)+".";
        beginIndx+=8;
        endIndx+=8;
    }
       return decimalIp.substring(0,decimalIp.length()-1);

}
}
