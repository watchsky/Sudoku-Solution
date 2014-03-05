package com.wu.homework;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by xiangwu on 3/3/14.
 */
public class LayoutTest {

    @Test
    public void should_not_throw_exception_if_only_one_empty_cells() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 0, 5}}, 3);
        assertThat(layout, is(not(nullValue())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_two_empty_cells() throws Exception {
        new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 0, 0}}, 3);
    }

    @Test
    public void should_move_left() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 0, 5}}, 3);

        Layout newLayout = layout.moveLeft();

        assertThat(newLayout, equalTo(new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {0, 7, 5}}, 3)));
    }

    @Test(expected = MovingOutOfBoundaryException.class)
    public void should_not_move_beyond_left_boundary() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {0, 7, 5}}, 3);

        Layout newLayout = layout.moveLeft();
    }

    @Test
    public void should_move_right() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 0, 5}}, 3);

        Layout newLayout = layout.moveRight();

        assertThat(newLayout, equalTo(new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 5, 0}}, 3)));
    }

    @Test(expected = MovingOutOfBoundaryException.class)
    public void should_not_move_beyond_right_boundary() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 5, 0}}, 3);

        Layout newLayout = layout.moveRight();
    }

    @Test
    public void should_move_up() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 0, 5}}, 3);

        Layout newLayout = layout.moveUp();

        assertThat(newLayout, equalTo(new Layout(new int[][]{{2, 8, 3}, {1, 0, 4}, {7, 6, 5}}, 3)));
    }

    @Test(expected = MovingOutOfBoundaryException.class)
    public void should_not_move_beyond_up_boundary() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 0}, {1, 6, 3}, {7, 5, 4}}, 3);

        Layout newLayout = layout.moveRight();
    }

    @Test
    public void should_move_down() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 0, 4}, {7, 6, 5}}, 3);

        Layout newLayout = layout.moveDown();

        assertThat(newLayout, equalTo(new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 0, 5}}, 3)));
    }

    @Test(expected = MovingOutOfBoundaryException.class)
    public void should_not_move_beyond_down_boundary() throws Exception {
        Layout layout = new Layout(new int[][]{{2, 8, 3}, {1, 6, 4}, {7, 5, 0}}, 3);

        Layout newLayout = layout.moveDown();
    }
}
