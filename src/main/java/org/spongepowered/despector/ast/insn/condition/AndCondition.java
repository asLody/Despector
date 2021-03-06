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
package org.spongepowered.despector.ast.insn.condition;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import org.spongepowered.despector.ast.AstVisitor;
import org.spongepowered.despector.util.serialization.AstSerializer;
import org.spongepowered.despector.util.serialization.MessagePacker;

import java.io.IOException;
import java.util.List;

/**
 * A condition for the and of several other conditions.
 */
public class AndCondition extends Condition {

    private final List<Condition> args;

    public AndCondition(Condition... args) {
        this.args = Lists.newArrayList(checkNotNull(args, "args"));
        checkArgument(this.args.size() >= 2, "Not enough operands");
    }

    public AndCondition(Iterable<Condition> args) {
        this.args = Lists.newArrayList(checkNotNull(args, "args"));
        checkArgument(this.args.size() >= 2, "Not enough operands");
    }

    /**
     * Gets the operands of this condition.
     */
    public List<Condition> getOperands() {
        return this.args;
    }

    @Override
    public void accept(AstVisitor visitor) {
        if (visitor instanceof ConditionVisitor) {
            ((ConditionVisitor) visitor).visitAndCondition(this);
            for (Condition c : this.args) {
                c.accept(visitor);
            }
        }
    }

    @Override
    public void writeTo(MessagePacker pack) throws IOException {
        pack.startMap(2);
        pack.writeString("id").writeInt(AstSerializer.CONDITION_ID_AND);
        pack.writeString("args").startArray(this.args.size());
        for (Condition arg : this.args) {
            arg.writeTo(pack);
        }
        pack.endArray();
        pack.endMap();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.args.size(); i++) {
            Condition arg = this.args.get(i);
            if (arg instanceof OrCondition) {
                sb.append("(");
                sb.append(this.args.get(i));
                sb.append(')');
            } else {
                sb.append(this.args.get(i));
            }
            if (i < this.args.size() - 1) {
                sb.append(" && ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AndCondition)) {
            return false;
        }
        AndCondition and = (AndCondition) o;
        if (and.getOperands().size() != this.args.size()) {
            return false;
        }
        for (int i = 0; i < this.args.size(); i++) {
            if (!this.args.get(i).equals(and.getOperands().get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 1;
        for (Condition cond : this.args) {
            h = h * 37 + cond.hashCode();
        }
        return h;
    }

}
