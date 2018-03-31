/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import org.hyperic.sigar.Sigar;

/**
 *
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
     
        
        Sigar sigar = new Sigar();
        System.out.println(sigar.getMem().getActualUsed());
        System.out.println(sigar.getMem().getRam());
        System.out.println(sigar.getMem().getTotal());
        System.out.println(sigar.getMem().getFreePercent());
        System.out.println(sigar.getMem().getUsedPercent());
        System.out.println(sigar.getMem().getUsed());
        System.out.println(((double)sigar.getMem().getActualUsed())/sigar.getMem().getTotal());
        
        System.out.println(sigar.getCpuPerc().getIdle());
    }

}
