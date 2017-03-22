/*
 * The MIT License (MIT)
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
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
package org.spongepowered.despector.ast.members.insn.assign;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.despector.ast.members.insn.InstructionVisitor;
import org.spongepowered.despector.ast.members.insn.arg.Instruction;

public class InstanceFieldAssignment extends FieldAssignment {

    private Instruction owner;

    public InstanceFieldAssignment(String field_name, String type_desc, String owner_type, Instruction owner, Instruction val) {
        super(field_name, type_desc, owner_type, val);
        this.owner = checkNotNull(owner, "owner");
    }

    public Instruction getOwner() {
        return this.owner;
    }

    public void setOwner(Instruction owner) {
        this.owner = checkNotNull(owner, "owner");
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.visitInstanceFieldAssign(this);
        this.owner.accept(visitor);
        this.val.accept(visitor);
    }

    @Override
    public String toString() {
        return this.owner + "." + this.field_name + " = " + this.val + ";";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InstanceFieldAssignment)) {
            return false;
        }
        InstanceFieldAssignment insn = (InstanceFieldAssignment) obj;
        return this.val.equals(insn.val) && this.owner.equals(insn.owner);
    }

}
