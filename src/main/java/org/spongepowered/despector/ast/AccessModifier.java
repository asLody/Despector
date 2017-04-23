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
package org.spongepowered.despector.ast;

import org.spongepowered.despector.decompiler.BaseDecompiler;

/**
 * Represents the access modifier of a class member.
 */
public enum AccessModifier {

    PUBLIC("public"),
    PROTECTED("protected"),
    PACKAGE_PRIVATE(""),
    PRIVATE("private");

    private final String ident;

    AccessModifier(String ident) {
        this.ident = ident;
    }

    /**
     * Gets the string representation of this access modifier.
     */
    public String asString() {
        return this.ident;
    }

    /**
     * Gets the access modifier from the given flags. See
     * {@link Opcodes#ACC_PUBLIC}, {@link Opcodes#ACC_PROTECTED},
     * {@link Opcodes#ACC_PRIVATE}. A flag with none of these set will be marked
     * as {@link #PACKAGE_PRIVATE}.
     */
    public static AccessModifier fromModifiers(int access) {
        if ((access & BaseDecompiler.ACC_PUBLIC) != 0) {
            return PUBLIC;
        } else if ((access & BaseDecompiler.ACC_PROTECTED) != 0) {
            return PROTECTED;
        } else if ((access & BaseDecompiler.ACC_PRIVATE) != 0) {
            return PRIVATE;
        }
        return PACKAGE_PRIVATE;
    }

}
