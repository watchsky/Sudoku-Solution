package com.wu.homework;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by xiangwu on 2/27/14.
 */
public class DepthFirstSearcherTest {

    @Test
    public void should_return_0_steps_if_init_and_result_layout_is_the_same() throws Exception {
        final int[][] initMapArray = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        final int[][] expectedResultMapArray = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        DepthFirstSearcher depthFirstSearcher = new DepthFirstSearcher(new Layout(initMapArray, 3));

        SearchedPath path = depthFirstSearcher.search(expectedResultMapArray);

        assertThat(path, is(not(nullValue())));
        assertThat(path.getLength(), is(0));
    }

    @Test
    public void should_return_null_if_no_path_can_be_found() throws Exception {
        final int[][] initMapArray = {{1, 2, 3}, {4, 0, 8}, {7, 6, 5}};
        final int[][] expectedResultMapArray = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        DepthFirstSearcher depthFirstSearcher = new DepthFirstSearcher(new Layout(initMapArray, 3));

        SearchedPath path = depthFirstSearcher.search(expectedResultMapArray);

        assertThat(path, is(nullValue()));
    }

    @Test
    public void should_return_steps_clockwise_rotate_1() throws Exception {
        final int[][] initMapArray = {
                {1, 3, 0},
                {8, 2, 4},
                {7, 6, 5}};
        final int[][] expectedResultMapArray = {
                {1, 0, 3},
                {8, 2, 4},
                {7, 6, 5}};
        DepthFirstSearcher depthFirstSearcher = new DepthFirstSearcher(new Layout(initMapArray, 3));

        SearchedPath path = depthFirstSearcher.search(expectedResultMapArray);

        assertThat(path, is(not(nullValue())));
        assertThat(new Layout(initMapArray, 3).step(path), is(new Layout(expectedResultMapArray, 3)));
    }

    @Test
    public void should_return_steps_clockwise_rotate_2() throws Exception {
        final int[][] initMapArray = {
                {1, 3, 0},
                {8, 2, 4},
                {7, 6, 5}};
        final int[][] expectedResultMapArray = {
                {0, 1, 3},
                {8, 2, 4},
                {7, 6, 5}};
        DepthFirstSearcher depthFirstSearcher = new DepthFirstSearcher(new Layout(initMapArray, 3));

        SearchedPath path = depthFirstSearcher.search(expectedResultMapArray);

        assertThat(path, is(not(nullValue())));
        assertThat(new Layout(initMapArray, 3).step(path), is(new Layout(expectedResultMapArray, 3)));
    }

    @Test
    public void should_return_steps() throws Exception {
        final int[][] initMapArray = {
                {2, 8, 3},
                {1, 6, 4},
                {7, 0, 5}};
        final int[][] expectedResultMapArray = {
                {1, 2, 3},
                {8, 0, 4},
                {7, 6, 5}};
        DepthFirstSearcher depthFirstSearcher = new DepthFirstSearcher(new Layout(initMapArray, 3));

        SearchedPath path = depthFirstSearcher.search(expectedResultMapArray);

        assertThat(path, is(not(nullValue())));
        assertThat(new Layout(initMapArray, 3).step(path), is(new Layout(expectedResultMapArray, 3)));
    }

}
