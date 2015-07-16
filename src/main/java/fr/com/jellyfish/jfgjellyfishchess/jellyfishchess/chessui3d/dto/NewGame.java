/**
 * *****************************************************************************
 * Copyright (c) 2015, Thomas.H Warner. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto;

/**
 *
 * @author thw
 */
public class NewGame {

    //<editor-fold defaultstate="collapsed" desc="private vars">
    /**
     * UI side color.
     */
    private final String uiColor;

    /**
     * Sleep time used after reloading data.
     */
    private final long sleepMS;

    /**
     * Is restart finished ?
     */
    private boolean restarted = false;
    
    /**
     * Are hints enabled.
     */
    private boolean hintsEnabled;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param uiColor
     * @param sleepMS
     * @param hintsEnabled
     */
    public NewGame(final String uiColor, final long sleepMS, final boolean hintsEnabled) {
        this.uiColor = uiColor;
        this.sleepMS = sleepMS;
        this.hintsEnabled = hintsEnabled;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public boolean isRestarted() {
        return restarted;
    }

    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    public boolean isHintsEnabled() {
        return hintsEnabled;
    }
    
    public String getUiColor() {
        return uiColor;
    }

    public long getSleepMS() {
        return sleepMS;
    }
    //</editor-fold> 

}