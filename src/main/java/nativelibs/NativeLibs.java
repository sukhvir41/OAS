/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelibs;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author development
 */
public class NativeLibs {

    private List<String> libs = Arrays.asList("wpacp.dll", "Packet.dll", "sigar-amd64-winnt.dll", "sigar-x86-winnt.dll");
    private final static File file = new File(NativeLibs.class.getResource("loaded.txt").getFile());

    static {

    }

//    private NativeLibs() throws InstantiationException {
//        throw new InstantiationException();
//    }
//
//    public static void loadLibs() {
//        try {
//            if (!getFileStatus()) {
//                System.out.println("loaded +++++++++++++++++++++++++++++++++++++++++++");
//                System.load(NativeLibs.class.getResource("wpcap.dll").getFile());
//                System.load(NativeLibs.class.getResource("Packet.dll").getFile());
//
//                if (ArchName.is64()) {
//                    System.load(NativeLibs.class.getResource("sigar-amd64-winnt.dll").getFile());
//                } else {
//                    System.load(NativeLibs.class.getResource("sigar-x86-winnt.dll").getFile());
//                }
//                writeStatus(true);
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            System.out.println("error in loading native libs please check  ============================");
//        }
//    }
//
//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize(); //To change body of generated methods, choose Tools | Templates.
//        writeStatus(false);
//    }
//
//    public static boolean getFileStatus() {
//        BufferedReader reader = null;
//        FileReader fileReader = null;
//
//        try {
//            fileReader = new FileReader(file);
//            reader = new BufferedReader(fileReader);
//            String status = reader.readLine();
//            return Boolean.parseBoolean(status);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return false;
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//                if (fileReader != null) {
//                    fileReader.close();
//                }
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    public static void writeStatus(boolean status) {
//        BufferedWriter writer = null;
//        FileWriter fileWriter = null;
//
//        try {
//            fileWriter = new FileWriter(file);
//            writer = new BufferedWriter(fileWriter);
//            writer.write(String.valueOf(status));
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        } finally {
//            try {
//                if (writer != null) {
//                    writer.close();
//                }
//                if (fileWriter != null) {
//                    fileWriter.close();
//                }
//            } catch (Exception e) {
//            }
//        }
//
//    }

}
