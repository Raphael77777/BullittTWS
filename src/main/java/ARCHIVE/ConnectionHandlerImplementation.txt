package ConnectionHandling;

import com.ib.controller.ApiController;

import java.util.List;

public class ConnectionHandlerImplementation implements ApiController.IConnectionHandler {
    @Override
    public void connected() {
        //Do something when connected
        System.out.println("Connected");
    }
    @Override
    public void disconnected() {
        //Do something when disconnected
        System.out.println("Disonnected");
    }

    @Override
    public void accountList(List<String> list) {
        //Do something with the account list
        for (String s : list){
            System.out.println("ACCOUNT : "+s);
        }
    }
    @Override
    public void error(Exception e) {
        //Do something on error
    }
    @Override
    public void message(int id, int errorCode, String errorMsg) {
        //Do something with server messages
        System.out.println(errorCode + " : " + errorMsg);
    }
    @Override
    public void show(String string) {
        //Do something with parameter
    }
}
