import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ViewerLogin extends JFrame implements ActionListener{

    JPanel pnl = new JPanel();
   static JTextField Username = new JTextField("", 10);
   static JTextField Password = new JTextField("", 10);
    JButton submit = new JButton("Submit");
    JLabel lbl1 = new JLabel("Enter Login name: ");
    JLabel lbl2 = new JLabel("Enter Password: ");

    Font customFont = new Font("Serif", Font.PLAIN, 32);


    /*boolean isAccount = false;
    String playerUser, playerPass;
*/
    public ViewerLogin(){
        super("ViewerLoginListener");

        pnl.setBackground(Color.pink);

        add(pnl);

        lbl1.setFont(customFont);
        lbl2.setFont(customFont);

        pnl.add(lbl1);
        pnl.add(Username);
        pnl.add(lbl2);
        pnl.add(Password);
        pnl.add(submit);

      /*  Username.addActionListener(this);
        Password.addActionListener(this);
        submit.addActionListener(this);
*/
        submit.addActionListener(this);
        setVisible(true);
        pack();
    }
    @Override
    public void actionPerformed(ActionEvent event) {


        //ViwerObserving vo = new ViwerObserving();
        ViewTransmitter tx = new ViewTransmitter();
        @SuppressWarnings("unused")
		GameView view = new GameView(tx);

     /*   playerUser = Username.getText();
        playerPass = Password.getText();

        setPlayerUser(playerUser);
        setPlayerPass(playerPass);

        isAccount = playerData.checkAccount(playerUser, playerPass);
        System.out.println(isAccount);
        if(isAccount == false){
            playerData.loginUnsuccessful();
        }
        if(isAccount == true){
            playerData.loginSuccessful();
        }
    }
    public String getPlayerUser() {
        return playerUser;
    }
    public void setPlayerUser(String playerUser) {
        this.playerUser = playerUser;
    }
    public String getPlayerPass() {
        return playerPass;
    }
    public void setPlayerPass(String playerPass) {
        this.playerPass = playerPass; */
    }
}
