package com.dfsek.terra.addons.terrascript.functions;

import com.dfsek.terra.addons.terrascript.api.Function;
import com.dfsek.terra.addons.terrascript.api.ImplementationArguments;
import com.dfsek.terra.addons.terrascript.api.Position;
import com.dfsek.terra.addons.terrascript.api.lang.ConstantExpression;
import com.dfsek.terra.addons.terrascript.api.lang.Returnable;
import com.dfsek.terra.addons.terrascript.api.lang.Variable;
import com.dfsek.terra.addons.terrascript.api.buffer.items.BufferedBlock;
import com.dfsek.terra.addons.terrascript.api.TerraImplementationArguments;
import com.dfsek.terra.api.TerraPlugin;
import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.properties.Context;
import com.dfsek.terra.api.util.RotationUtil;
import com.dfsek.terra.api.vector.Vector2;
import com.dfsek.terra.api.vector.Vector3;
import net.jafama.FastMath;

import java.util.HashMap;
import java.util.Map;

public class BlockFunction implements Function<Void> {
    protected final Returnable<Number> x, y, z;
    protected final Returnable<String> blockData;
    protected final TerraPlugin main;
    private final Map<String, BlockState> data = new HashMap<>();
    private final Returnable<Boolean> overwrite;
    private final Position position;

    public BlockFunction(Returnable<Number> x, Returnable<Number> y, Returnable<Number> z, Returnable<String> blockData, Returnable<Boolean> overwrite, TerraPlugin main, Position position) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockData = blockData;
        this.overwrite = overwrite;
        this.main = main;
        this.position = position;
    }

    void setBlock(ImplementationArguments implementationArguments, Map<String, Variable<?>> variableMap, TerraImplementationArguments arguments, BlockState rot) {
        Vector2 xz = new Vector2(x.apply(, implementationArguments, variableMap).doubleValue(), z.apply(, implementationArguments, variableMap).doubleValue());

        RotationUtil.rotateVector(xz, arguments.getRotation());

        RotationUtil.rotateBlockData(rot, arguments.getRotation().inverse());
        arguments.getBuffer().addItem(new BufferedBlock(rot, overwrite.apply(, implementationArguments, variableMap), main, arguments.isWaterlog()), new Vector3(FastMath.roundToInt(xz.getX()), y.apply(, implementationArguments, variableMap).doubleValue(), FastMath.roundToInt(xz.getZ())));
    }

    @Override
    public Void apply(Context context, Map<String, Variable<?>> variableMap) {
        TerraImplementationArguments arguments = (TerraImplementationArguments) implementationArguments;
        BlockState rot = getBlockState(implementationArguments, variableMap).clone();
        setBlock(implementationArguments, variableMap, arguments, rot);
        return null;
    }

    protected BlockState getBlockState(ImplementationArguments arguments, Map<String, Variable<?>> variableMap) {
        return data.computeIfAbsent(blockData.apply(, arguments, variableMap), main.getWorldHandle()::createBlockData);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    public static class Constant extends BlockFunction {
        private final BlockState state;

        public Constant(Returnable<Number> x, Returnable<Number> y, Returnable<Number> z, ConstantExpression<String> blockData, Returnable<Boolean> overwrite, TerraPlugin main, Position position) {
            super(x, y, z, blockData, overwrite, main, position);
            this.state = main.getWorldHandle().createBlockData(blockData.getConstant());
        }

        @Override
        protected BlockState getBlockState(ImplementationArguments arguments, Map<String, Variable<?>> variableMap) {
            return state;
        }
    }
}
