package OnlineVotingSystem;
import java.sql.*;
public class Conn {
    Connection c;
    Statement s;
    public Conn(){

        try{
            //Class.forName("");
            c=DriverManager.getConnection("jdbc:mysql:///onlinevoting","root","Santok12345@");
            s=c.createStatement();
        }
        catch (Exception e){
            System.out.println("Sorry some error occured");
        }
    }
}
