/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils;

import igc_utils.exceptions.EmptyIgcFileException;
import igc_utils.exceptions.NoIgcFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jfrese
 */
public class IgcFile extends File {

    private final List<String> fileContent;
    Flight flight;

    public IgcFile(String pathname) throws NoIgcFileException {
        super(pathname);
        fileContent = new ArrayList<>();
        if (!this.getAbsolutePath().contains(".igc")) {
            throw new NoIgcFileException("The choosen File isn't an valid Igc-File.", this.getName());
        }
    }

    public IgcFile(URI uri) throws NoIgcFileException {
        super(uri);
        fileContent = new ArrayList<>();
        if (!this.getAbsolutePath().contains(".igc")) {
            throw new NoIgcFileException("The choosen File isn't an valid Igc-File.", this.getName());
        }
    }

    public IgcFile(File parent, String child) throws NoIgcFileException {
        super(parent, child);
        fileContent = new ArrayList<>();
        if (!this.getAbsolutePath().contains(".igc")) {
            throw new NoIgcFileException("The choosen File isn't an valid Igc-File.", this.getName());
        }
    }

    public IgcFile(String parent, String child) throws NoIgcFileException {
        super(parent, child);
        fileContent = new ArrayList<>();
        if (!this.getAbsolutePath().contains(".igc")) {
            throw new NoIgcFileException("The choosen File isn't an valid Igc-File.", this.getName());
        }
    }

    public List<String> getFileContent() throws EmptyIgcFileException {
        if (fileContent.isEmpty()) {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(this));
                String line = br.readLine();

                while (line != null) {
                    fileContent.add(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(IgcFile.class.getName()).log(Level.SEVERE, "Reader Error.", ex);
            }

            if (fileContent.isEmpty()) {
                throw new EmptyIgcFileException("Igc-File is empty!", this.getName());
            }
        }
        return fileContent;
    }

    public String getfileContent() {
        if (fileContent.isEmpty()) {
            try {
                getFileContent();
            } catch (EmptyIgcFileException ex) {
                Logger.getLogger(IgcFile.class.getName()).log(Level.SEVERE, "Could not initialise filereader.", ex);
            }
        }

        StringBuilder sb = new StringBuilder();

        for (String line : fileContent) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public Flight getFligth() {
        if (flight == null) {
            if (fileContent.isEmpty()) {
                try {
                    getFileContent();
                } catch (EmptyIgcFileException ex) {
                    Logger.getLogger(IgcFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            flight = new Flight(fileContent,this.getName());
        }
        return flight;
    }
}
