/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.chrisbotcom.boomerang.types;

import org.bukkit.util.BlockVector;

/**
 *
 * @author Chris
 */
public class SelectionType {
    BlockVector pos1;
    BlockVector pos2;

    public BlockVector getPos1() {
        return pos1;
    }

    public void setPos1(BlockVector pos1) {
        this.pos1 = pos1;
    }

    public BlockVector getPos2() {
        return pos2;
    }

    public void setPos2(BlockVector pos2) {
        this.pos2 = pos2;
    }
    
    public boolean isValid() {
        return (this.pos1 != null) && (this.pos2 != null);
    }
}
