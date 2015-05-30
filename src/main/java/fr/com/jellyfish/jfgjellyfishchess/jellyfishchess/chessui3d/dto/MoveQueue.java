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
 ******************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import java.util.LinkedHashMap;

/**
 *
 * @author thw
 */
public class MoveQueue {
    
    /**
     * Move collection.
     */
    private final LinkedHashMap<String, Move> moves = new LinkedHashMap<>();
    
    /**
     * Move counter.
     */
    private Integer counter = 0;

    /**
     * 
     * @param move 
     */
    public void appendToEnd(final Move move) {
        ++counter;
        moves.put(String.valueOf(counter), move);
    }
    
    /**
     * 
     */
    public void clearQueue() {
       moves.clear();
       counter = 0;
    }
    
    /**
     * @param key 
     * @param move 
     * @return  
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException  
     */
    public boolean removeFromQueue(final String key, final Move move) throws MoveIndexOutOfBoundsException {
        
        if (this.counter < 1) {
            throw new MoveIndexOutOfBoundsException(String.format(MoveIndexOutOfBoundsException.MESSAGE_1, 
                    String.valueOf(this.counter)));
        }
        
        final Move removed = this.moves.remove(key);
        
        // TODO : return removed.equals(move)
        
        --counter;
        return true;
    }
        
    /**
     * 
     * @return 
     */
    public LinkedHashMap<String, Move> getMoves() {
        return moves;
    }
    
    public Integer getCounter() {
        return counter;
    }
    
}
