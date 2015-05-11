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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessBoard;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Move;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.ErroneousChessPositionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.GameTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Rook;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.ChessGameBuildException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidInfiniteSearchResult;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.ChessGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIMessage;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIProtocolDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.ChessGameBuilderUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thw
 */
public class OPENGLUIDriver extends AbstractChessGameDriver {
    
    //<editor-fold defaultstate="collapsed" desc="vars">
    /**
     * Chess board instance reference.
     */
    private ChessBoard board;
    
    /**
     * OPENGLUIHelper instance reference.
     */
    private OPENGLUIHelper helper;

    /**
     * ChessGame instance.
     */
    public ChessGame game = null;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     */
    public OPENGLUIDriver() {
        init();
        initDriverObservation();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="init">
    /**
     * Initialize Drier.
     * @param restart
     * @param loadingPreviousGame
     */
    private void init() {
        
        UCIProtocolDriver.getInstance().getIoExternalEngine().clearObservers();
        UCIProtocolDriver.getInstance().getIoExternalEngine().addExternalEngineObserver(this);
        
        try {
            this.game = ChessGameBuilderUtils.buildGame(this, GameTypeConst.CHESS_GAME,
                    'b',
                    'w',
                    5,
                    false,
                    0);
        } catch (final ChessGameBuildException ex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * Add this to all necessary observer patterns.
     */
    private void initDriverObservation() {
        
        // Add this class to Rook class's CastlingObservers.
        Rook rookA1 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.A1).getOnPositionChessMan();
        rookA1.addCastlingObserver(this);
        Rook rookA8 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.A8).getOnPositionChessMan();
        rookA8.addCastlingObserver(this);
        Rook rookH1 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.H1).getOnPositionChessMan();
        rookH1.addCastlingObserver(this);
        Rook rookH8 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.H8).getOnPositionChessMan();
        rookH8.addCastlingObserver(this);
        
        // Add this as observer on all Pawn classes for "En passant" moving.
        // Black Pawns :
        Pawn pawnH7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.H7).getOnPositionChessMan();
        pawnH7.addPawnEnPassantObserver(this);
        Pawn pawnG7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.G7).getOnPositionChessMan();
        pawnG7.addPawnEnPassantObserver(this);
        Pawn pawnF7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.F7).getOnPositionChessMan();
        pawnF7.addPawnEnPassantObserver(this);
        Pawn pawnE7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.E7).getOnPositionChessMan();
        pawnE7.addPawnEnPassantObserver(this);
        Pawn pawnD7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.D7).getOnPositionChessMan();
        pawnD7.addPawnEnPassantObserver(this);
        Pawn pawnC7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.C7).getOnPositionChessMan();
        pawnC7.addPawnEnPassantObserver(this);
        Pawn pawnB7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.B7).getOnPositionChessMan();
        pawnB7.addPawnEnPassantObserver(this);
        Pawn pawnA7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.A7).getOnPositionChessMan();
        pawnA7.addPawnEnPassantObserver(this);
        // White Pawns :
        Pawn pawnA2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.A2).getOnPositionChessMan();
        pawnA2.addPawnEnPassantObserver(this);
        Pawn pawnB2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.B2).getOnPositionChessMan();
        pawnB2.addPawnEnPassantObserver(this);
        Pawn pawnC2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.C2).getOnPositionChessMan();
        pawnC2.addPawnEnPassantObserver(this);
        Pawn pawnD2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.D2).getOnPositionChessMan();
        pawnD2.addPawnEnPassantObserver(this);
        Pawn pawnE2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.E2).getOnPositionChessMan();
        pawnE2.addPawnEnPassantObserver(this);
        Pawn pawnF2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.F2).getOnPositionChessMan();
        pawnF2.addPawnEnPassantObserver(this);
        Pawn pawnG2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.G2).getOnPositionChessMan();
        pawnG2.addPawnEnPassantObserver(this);
        Pawn pawnH2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.H2).getOnPositionChessMan();
        pawnH2.addPawnEnPassantObserver(this);

        // Add this as check observer to all chessmen :
        for (String position : BoardConst.boardPositions) {
            Board.getInstance().getCoordinates().get(position).getOnPositionChessMan(
                ).setCheckObserver(this);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Overriden Interface methods">   
    @Override
    public void engineResponse() { }
    
    @Override
    public void engineResponse(final String response, final int msgLevel) { 
        // TODO : display in text area console.
        System.out.println("response: " + response.replaceAll("\n", ""));
    }
    
    @Override
    public void engineMoved(final UCIMessage message) { 
        
        if (game.getColorToPLay().toLowerCase().toCharArray()[0] == game.getEngineColor()) {
            // TODO : notify move.
        } else {
            // TODO : wrong turn.
        }
        
        // Apply move to GUI.
        // Check legth of message : if == 4 then split in 2 halfs.
        // == 5 is a pawn promotion.
        char promotion = ' ';
        char promotionColor = ' ';
        boolean pawnPromotion = false;
        final String posFrom;
        final String posTo;

        if (message.getBestMove().length() == 4 || message.getBestMove().length() == 5) {

            posFrom = (String.valueOf(message.getBestMove().toCharArray()[0]) +
                    String.valueOf(message.getBestMove().toCharArray()[1]));
            posTo = (String.valueOf(message.getBestMove().toCharArray()[2]) +
                    String.valueOf(message.getBestMove().toCharArray()[3]));

            if (message.getBestMove().length() == 5) {

                pawnPromotion = true;
                // Get promotion type. Ex : a7a8q 'q' meaning Queen.
                promotion = message.getBestMove().toCharArray()[4];
                // Also determinate promotion color.
                if (this.getEngineColor().equals(BoardConst.BLACK)) {
                    promotionColor = 'b';
                } else if (this.getEngineColor().equals(BoardConst.WHITE)) {
                    promotionColor = 'w';
                }
            }

            try {

                if (game.executeMove(posFrom, posTo, false, pawnPromotion, promotion)) {

                    if (pawnPromotion) {
                        
                        // TODO.
                        
                    } else {
                        try {
                            helper.engineMovePositions.appendToEnd(
                                    new Move(ChessPositions.get(posFrom), ChessPositions.get(posTo)));
                        } catch (final ErroneousChessPositionException ex) {
                            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    // Free GUI so that it can move again.
                    this.setEngineSearching(false);

                } else {
                    throw new InvalidMoveException(message.getBestMove() + " is not a valid move.");
                }

            } catch (InvalidMoveException | PawnPromotionException ex) {
                Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.WARNING, null, ex);
            }
            
            // Finally, is checkmate from engine ? :
            if (message.getMessage().contains(UCIConst.NONE) && 
                    this.game.getMoveCount() >= UCIConst.FOOLS_MATE) {
                // TOTO notify checkmate.
            }
        }
    }
    
    @Override
    public void engineInfiniteSearchResponse(final UCIMessage uciMessage) throws InvalidInfiniteSearchResult { }

    @Override
    public void applyCastling(final String posFrom, final String posTo) { }
    
    @Override
    public void applyPawnEnPassant(final String virtualPawnPosition) { }
    
    @Override
    public void applyCheckSituation(final Position king, final boolean inCheck) { }
    
    @Override
    public void tick(final String displayTime) { 
        Game3D.current_game_time = displayTime;
    } 
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter & setters">   
    public void setBoard(final ChessBoard board) {
        this.board = board;
    }
    
    public void setHelper(OPENGLUIHelper helper) {
        this.helper = helper;
    }
    //</editor-fold>
    
}