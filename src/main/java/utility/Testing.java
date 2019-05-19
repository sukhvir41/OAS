/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Student;
import org.apache.commons.lang3.StringUtils;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;
import oshi.hardware.Networks;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
        SystemInfo info = new SystemInfo();
        Stream.of(info.getHardware().getNetworkIFs())
                .forEach( e -> System.out.println(Arrays.toString(e.getIPv4addr())));
    }



}