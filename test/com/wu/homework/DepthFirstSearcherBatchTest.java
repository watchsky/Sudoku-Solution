package com.wu.homework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class DepthFirstSearcherBatchTest {

    private final int[][] initLayout = new int[][]{{1, 3, 0}, {8, 2, 4}, {7, 6, 5}};
    private final int[][] resultLayout;
    private String description;

    public DepthFirstSearcherBatchTest(String description, final int[][] resultLayout) {
        this.description = description;
        this.resultLayout = resultLayout;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][]{
                {"clockwise-1",
                        new int[][]{{1, 0, 3}, {8, 2, 4}, {7, 6, 5}}},
                {"clockwise-1",
                        new int[][]{{0, 1, 3}, {8, 2, 4}, {7, 6, 5}}},
                {"clockwise-1",
                        new int[][]{{8, 1, 3}, {0, 2, 4}, {7, 6, 5}}}
        });
    }

    @Test
    public void should_return_steps_clockwise_rotate() throws Exception {
        DepthFirstSearcher depthFirstSearcher = new DepthFirstSearcher(new Layout(initLayout, 3));

        SearchedPath path = depthFirstSearcher.search(resultLayout);

        assertThat(path, is(not(nullValue())));
        assertThat(new Layout(initLayout, 3).step(path), is(new Layout(resultLayout, 3)));
    }

}
