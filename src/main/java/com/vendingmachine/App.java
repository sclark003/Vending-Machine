package com.vendingmachine;

import com.vendingmachine.controller.VendingMachineController;
import com.vendingmachine.controller.VendingMachineServiceLayerImpl;
import com.vendingmachine.dao.VendingMachineAuditDao;
import com.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.vendingmachine.dao.VendingMachineDao;
import com.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.vendingmachine.service.Change;
import com.vendingmachine.service.VendingMachineServiceLayer;
import com.vendingmachine.ui.UserIO;
import com.vendingmachine.ui.UserIOConsoleImpl;
import com.vendingmachine.ui.VendingMachineView;

public class App {

    public static void main(String[] args) {

        UserIO myIo = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        Change myChange = new Change();
        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao, myChange);
        VendingMachineController myController = new VendingMachineController(myService, myView);
        myController.run();

    }

}
