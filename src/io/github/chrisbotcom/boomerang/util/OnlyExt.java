/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.chrisbotcom.boomerang.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Chris
 */
public class OnlyExt implements FilenameFilter {
    String ext;

    public OnlyExt(String ext) {
        this.ext = ("." + ext).toLowerCase();
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(ext);
    }
}
