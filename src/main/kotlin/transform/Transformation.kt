@file:Suppress("NOTHING_TO_INLINE")

package geomx.transform

public interface Transformable<T : Transformable<T>> {
    public infix fun transformBy(t: Transformation<*>): T
}

public interface Transformation<T : Transformation<T>> : Transformable<T> {
    public val xx: Double get() = 1.0
    public val xy: Double get() = 0.0
    public val xz: Double get() = 0.0
    public val xw: Double get() = 0.0

    public val yx: Double get() = 0.0
    public val yy: Double get() = 1.0
    public val yz: Double get() = 0.0
    public val yw: Double get() = 0.0

    public val zx: Double get() = 0.0
    public val zy: Double get() = 0.0
    public val zz: Double get() = 1.0
    public val zw: Double get() = 0.0

    public val tx: Double get() = 0.0
    public val ty: Double get() = 0.0
    public val tz: Double get() = 0.0
    public val tw: Double get() = 1.0

    public interface Builder<T : Transformation<T>> {
        public fun build(
            xx: Double = 1.0, xy: Double = 0.0, xz: Double = 0.0, xw: Double = 0.0,
            yx: Double = 0.0, yy: Double = 1.0, yz: Double = 0.0, yw: Double = 0.0,
            zx: Double = 0.0, zy: Double = 0.0, zz: Double = 1.0, zw: Double = 0.0,
            tx: Double = 0.0, ty: Double = 0.0, tz: Double = 0.0, tw: Double = 1.0,
        ): T
    }
}

public infix fun <T : Transformable<T>> Transformation<*>.transform(t: T): T = t transformBy this

// Components

internal inline infix fun Transformation<*>.xx(t: Transformation<*>) = xx * t.xx + yx * t.xy + zx * t.xz + tx * t.xw
internal inline infix fun Transformation<*>.xy(t: Transformation<*>) = xy * t.xx + yy * t.xy + zy * t.xz + ty * t.xw
internal inline infix fun Transformation<*>.xz(t: Transformation<*>) = xz * t.xx + yz * t.xy + zz * t.xz + tz * t.xw
internal inline infix fun Transformation<*>.xw(t: Transformation<*>) = xw * t.xx + yw * t.xy + zw * t.xz + tw * t.xw

internal inline infix fun Transformation<*>.yx(t: Transformation<*>) = xx * t.yx + yx * t.yy + zx * t.yz + tx * t.yw
internal inline infix fun Transformation<*>.yy(t: Transformation<*>) = xy * t.yx + yy * t.yy + zy * t.yz + ty * t.yw
internal inline infix fun Transformation<*>.yz(t: Transformation<*>) = xz * t.yx + yz * t.yy + zz * t.yz + tz * t.yw
internal inline infix fun Transformation<*>.yw(t: Transformation<*>) = xw * t.yx + yw * t.yy + zw * t.yz + tw * t.yw

internal inline infix fun Transformation<*>.zx(t: Transformation<*>) = xx * t.zx + yx * t.zy + zx * t.zz + tx * t.zw
internal inline infix fun Transformation<*>.zy(t: Transformation<*>) = xy * t.zx + yy * t.zy + zy * t.zz + ty * t.zw
internal inline infix fun Transformation<*>.zz(t: Transformation<*>) = xz * t.zx + yz * t.zy + zz * t.zz + tz * t.zw
internal inline infix fun Transformation<*>.zw(t: Transformation<*>) = xw * t.zx + yw * t.zy + zw * t.zz + tw * t.zw

internal inline infix fun Transformation<*>.tx(t: Transformation<*>) = xx * t.tx + yx * t.ty + zx * t.tz + tx * t.tw
internal inline infix fun Transformation<*>.ty(t: Transformation<*>) = xy * t.tx + yy * t.ty + zy * t.tz + ty * t.tw
internal inline infix fun Transformation<*>.tz(t: Transformation<*>) = xz * t.tx + yz * t.ty + zz * t.tz + tz * t.tw
internal inline infix fun Transformation<*>.tw(t: Transformation<*>) = xw * t.tx + yw * t.ty + zw * t.tz + tw * t.tw

// Companion

public infix fun <T : Transformation<T>> Transformation.Builder<T>.from(t: Transformation<*>): T = build(
    xx = t.xx, xy = t.xy, xz = t.xz, xw = t.xw,
    yx = t.yx, yy = t.yy, yz = t.yz, yw = t.yw,
    zx = t.zx, zy = t.zy, zz = t.zz, zw = t.zw,
    tx = t.tx, ty = t.ty, tz = t.tz, tw = t.tw,
)

public fun <T : Transformation<T>> Transformation.Builder<T>.from(
    t: Transformation<*>,
    xx: Double = t.xx, xy: Double = t.xy, xz: Double = t.xz, xw: Double = t.xw,
    yx: Double = t.yx, yy: Double = t.yy, yz: Double = t.yz, yw: Double = t.yw,
    zx: Double = t.zx, zy: Double = t.zy, zz: Double = t.zz, zw: Double = t.zw,
    tx: Double = t.tx, ty: Double = t.ty, tz: Double = t.tz, tw: Double = t.tw,
): T = build(
    xx = xx, xy = xy, xz = xz, xw = xw,
    yx = yx, yy = yy, yz = yz, yw = yw,
    zx = zx, zy = zy, zz = zz, zw = zw,
    tx = tx, ty = ty, tz = tz, tw = tw,
)
