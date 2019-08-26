package utility;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateViews implements Plugin<Project> {


    public void createViews() {
        var file = new File("src/main/java/views");
        file.mkdir();
        var toScan = new File("src/main/webapp/WEB-INF");

        Map<String, String> filesToCompile = new HashMap<>();

        createFiles(List.of(toScan.listFiles()), filesToCompile, "Views");

        filesToCompile.forEach((name, code) -> createJavaFiles(name, code, file));
    }

    private void createFiles(List<File> theFiles, Map<String, String> filesToCompile, String className) {

        List<File> folders = new ArrayList<>();
        List<File> files = new ArrayList<>();

        for (File file : theFiles) {
            if (file.isDirectory()) {
                folders.add(file);
                createFiles(List.of(file.listFiles()), filesToCompile, WordUtils.capitalize(file.getName()));
            } else if (file.getName().endsWith(".jsp")) {
                files.add(file);
            }
        }

        var filesString = new StringBuilder();
        files.stream()
                .map(this::createFileStringVariable)
                .forEach(filesString::append);

        var folderString = new StringBuilder();
        folders.stream()
                .map(this::createFolderStringVariables)
                .forEach(folderString::append);

        String theClass = "" +
                "package views;" +
                "public class " + className + "{" +
                "   public static " + className + " " + className.toUpperCase() + " = new " + className + "();" +
                filesString +
                folderString +
                "}";

        filesToCompile.put(className, theClass);

    }

    private String createFileStringVariable(File file) {
        try {
            var name = file.getName().replace(".jsp", "");
            var path = file.getCanonicalPath();
            var variableValue = path.substring(StringUtils.indexOf(path, "\\WEB-INF")).replace("\\", "\\\\");
            return "public  String " + name.toUpperCase().replace('-', '_') + " = \"" + variableValue + "\";";

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String createFolderStringVariables(File file) {
        try {
            var name = WordUtils.capitalize(file.getName());
            var variable = name.toUpperCase();
            return "public " + name + " " + variable + " = new " + name + "();";

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void createJavaFiles(String name, String code, File destination) {
        try {
            var file = new File(destination.getCanonicalPath() + "\\" + name + ".java");
            Files.write(Paths.get(file.toURI()), code.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void apply(Project project) {
        createViews();
    }
}
