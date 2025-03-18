
package com.example.data_compression.logic;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileHandler {
    public static void writecompresedData(String data, Huffman.HuffmanNode root)  {
                try( ObjectOutputStream compressedFile = new ObjectOutputStream(new FileOutputStream("compressed_file.bin"))){
                    Huffman huffman = new Huffman();
                    if(root==null){
                        System.out.println("Root is null.");
                        return;
                    }
                    compressedFile.writeObject(root);
                    compressedFile.writeInt(data.length());
                    BitSet bits = new BitSet();
                    for(int i=0;i<data.length();i++){
                        if(data.charAt(i)=='1')
                        {
                            bits.set(i);
                        }
                    }
                    byte [] compressedData=bits.toByteArray();
                    compressedFile.write(compressedData);
                }catch(IOException e){
                e.printStackTrace();
            }
    }
    public static void writedecompressedData(String data)  {
        try(FileWriter decompressedFile = new FileWriter("decompressed_file.txt")){
            decompressedFile.write(data);
        } catch (IOException e){
            System.out.println("Error Writing file"+e.getMessage());
        }
    }
}
