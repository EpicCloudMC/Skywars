/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 EpicCloudMC <epiccloudmc.com> Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.epiccloudmc.skywars.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.epiccloudmc.skywars.Skywars;

public class Files {

  private static Skywars skywars = Skywars.getSkywars();
  public static final File ROOT =
      new File(skywars.getServer().getWorldContainer().getAbsolutePath());

  public static File create(File file, FileType type) {
    if (file == null) {
      Logs.severe("An attempt was made to create a file from a null instance.");
      return null;
    }
    try {
      if (type == FileType.FILE) {
        if (file.exists()) {
          if (file.isDirectory()) {
            file.delete();
            file.createNewFile();
          }
        } else {
          file.createNewFile();
        }
        return file;
      } else if (type == FileType.DIRECTORY) {
        if (file.exists()) {
          if (!file.isDirectory()) {
            file.delete();
            file.mkdirs();
          }
        } else {
          file.mkdirs();
        }
        return file;
      } else {
        return null;
      }
    } catch (IOException exception) {
      Logs.severe("An error occured whilst creating file: " + file.getName());
      return null;
    }
  }

  public static Boolean matches(File file, FileType type) {
    if (type == FileType.FILE) {
      if (file.isDirectory()) {
        return false;
      } else {
        return true;
      }
    } else if (type == FileType.DIRECTORY) {
      if (file.isDirectory()) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public static File copy(File file, String path, String... ignore) {
    if (file == null) {
      Logs.severe("An attempt was made to copy a file from a null instance.");
      return new File("");
    }
    try {
      File target = new File(path, file.getName());
      List<String> ignores = Arrays.asList(ignore);
      if (matches(file, FileType.FILE)) {
        if (!ignores.contains(file.getName())) {
          InputStream input = new FileInputStream(file);
          OutputStream output = new FileOutputStream(create(target, FileType.FILE));
          byte[] buffer = new byte[1024];
          int length;
          while ((length = input.read(buffer)) > 0)
            output.write(buffer, 0, length);
          input.close();
          output.close();
        }
      } else if (matches(file, FileType.DIRECTORY)) {
        for (File sourceFile : file.listFiles()) {
          copy(sourceFile, file.getAbsolutePath());
        }
      }
      return target;
    } catch (Exception exception) {
      Logs.severe("An error occured whilst copying file: " + file.getName());
      return new File("");
    }
  }

  public static FileType getType(File file) {
    if (matches(file, FileType.FILE)) {
      return FileType.FILE;
    } else if (matches(file, FileType.DIRECTORY)) {
      return FileType.DIRECTORY;
    } else {
      return FileType.UNKNOWN;
    }
  }

  public static Boolean exists(String path, String name) {
    return new File(path, name).exists();
  }

  public enum FileType {
    FILE, DIRECTORY, UNKNOWN;
  }
}
