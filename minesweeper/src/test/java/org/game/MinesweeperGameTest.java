package org.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinesweeperGameTest {
    private MinesweeperGame game;
    @BeforeEach
    public void setup() {
        game = new MinesweeperGame(4,3) ;

    }

    @Test
    public void testGridInitialization() {
        assertEquals(4, game.getGridSize());
        //assertEquals("-", game.getGridCell(0,0));  // initial grid cell is hidden
    }

    @Test
    public void testMinePlacement() {
       game.placeMines(); // Place 3 random mines
        assertEquals(3, game.getMineCount());  // initial grid cell is hidden
    }

//    @Test
//    public void testRevealSaveCell() {
//        game.forceMinePlacement(1,1); // force a mine at 1,1
//        assertEquals(0, game.revealCell(0,0));  // should reveal 0 adjacent mines
//    }

    @Test
    public void testRevealMineCell() {
        game.forceMinePlacement(1,1); // force a mine at 1,1
        assertThrows(RuntimeException.class, ()-> {
            game.revealCell(1,1); // triggered a mine
        });
    }

    @Test
    public void testOutOfBoundsMove() {
        assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
            game.revealCell(5,5); // out of bounds
        });
    }


}
