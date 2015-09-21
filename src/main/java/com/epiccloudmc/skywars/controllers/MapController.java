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
package com.epiccloudmc.skywars.controllers;

import java.io.File;

import com.epiccloudmc.skywars.objects.interfaces.IController;
import com.epiccloudmc.skywars.utils.Files;
import com.epiccloudmc.skywars.utils.Logs;
import com.epiccloudmc.skywars.utils.Files.FileType;

public class MapController extends IController {

  private File folder;

  public MapController() {
    folder = Files.create(new File(folder, "maps"), FileType.DIRECTORY);
    for (File map : folder.listFiles()) {
      if (Files.matches(map, FileType.DIRECTORY)) {
        load(map);
      }
    }
  }

  public void load(File map) {
    if (Files.exists(map.getParent(), map.getName())) {
      Logs.warn("A map already exists with the name: " + map.getName());
      return;
    }
    File world = Files.copy(map, Files.ROOT.getAbsolutePath(), "uid.dat", "session.dat");
  }
}
