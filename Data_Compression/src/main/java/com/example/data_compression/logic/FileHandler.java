
package com.example.data_compression.logic;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileHandler {
    public static void writeData(String data,int operation)  {
        if(operation==1)
        {
           try(FileWriter decompressedFile = new FileWriter("decompressed_file.txt")){
               decompressedFile.write(data);
           } catch (IOException e){
               System.out.println("Error Writing file"+e.getMessage());
           }
        }
        else{
                try( ObjectOutputStream compressedFile = new ObjectOutputStream(new FileOutputStream("compressed_file.bin"))){
                    Huffman huffman = new Huffman();
                    Huffman.HuffmanNode root= huffman.getRoot();
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
                System.out.println("Error Writing file"+e.getMessage());
            }
        }
    }
}