/*
 * The MIT License (MIT)
 *
 * Copyright (c) Despector <https://despector.voxelgenesis.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.despector.decompiler.method.graph;

import org.spongepowered.despector.decompiler.method.PartialMethod;
import org.spongepowered.despector.decompiler.method.graph.data.opcode.OpcodeBlock;

import java.util.List;
import java.util.Set;

/**
 * A producer for dividing up the opcodes into blocks and joining them together
 * into a graph.
 */
public interface GraphProducerStep {

    /**
     * Adds the indices of any opcodes that the opcode list should be split
     * after to the break_points set.
     */
    void collectBreakpoints(PartialMethod partial, Set<Integer> break_points);

    /**
     * Forms edges between blocks in the graph.
     */
    void formEdges(PartialMethod partial, List<Integer> sorted_break_points, List<OpcodeBlock> block_list);

    static OpcodeBlock find(List<OpcodeBlock> blocks, int op) {
        for (OpcodeBlock block : blocks) {
            if (block.getStart() <= op && block.getEnd() >= op) {
                return block;
            }
        }
        throw new IllegalStateException();
    }
}
