package com.dfsek.terra.addons.terrascript.functions;

import com.dfsek.terra.addons.terrascript.api.Function;
import com.dfsek.terra.addons.terrascript.api.Position;
import com.dfsek.terra.addons.terrascript.api.lang.Returnable;
import com.dfsek.terra.addons.terrascript.api.lang.Variable;
import com.dfsek.terra.addons.terrascript.api.TerraProperties;
import com.dfsek.terra.api.properties.Context;
import com.dfsek.terra.api.util.RotationUtil;
import com.dfsek.terra.api.vector.Vector2;
import com.dfsek.terra.api.vector.Vector3;
import net.jafama.FastMath;

import java.util.Map;

public class GetMarkFunction implements Function<String> {
    private final Returnable<Number> x, y, z;
    private final Position position;

    public GetMarkFunction(Returnable<Number> x, Returnable<Number> y, Returnable<Number> z, Position position) {
        this.position = position;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String apply(Context context, Map<String, Variable<?>> variableMap) {
        TerraProperties arguments = context.get(TerraProperties.class);
        Vector2 xz = new Vector2(x.apply(context, variableMap).doubleValue(), z.apply(context, variableMap).doubleValue());

        RotationUtil.rotateVector(xz, arguments.getRotation());
        String mark = arguments.getBuffer().getMark(new Vector3(FastMath.floorToInt(xz.getX()), FastMath.floorToInt(y.apply(context, variableMap).doubleValue()), FastMath.floorToInt(xz.getZ())));
        return mark == null ? "" : mark;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.STRING;
    }
}