package geomx.transform

import mathx.lerp

public data class Transform2D(
    override val xx: Double, override val xy: Double, override val xw: Double,
    override val yx: Double, override val yy: Double, override val yw: Double,
    override val tx: Double, override val ty: Double, override val tw: Double,
) : Transformation<Transform2D> {
    public fun interpolate(b: Transform2D, t: Double): Transform2D = Transform2D(
        xx = lerp(xx, b.xx, t), xy = lerp(xy, b.xy, t), xw = lerp(xw, b.xw, t),
        yx = lerp(yx, b.yx, t), yy = lerp(yy, b.yy, t), yw = lerp(yw, b.yw, t),
        tx = lerp(tx, b.tx, t), ty = lerp(ty, b.ty, t), tw = lerp(tw, b.tw, t),
    )

    override fun transformBy(t: Transformation<*>): Transform2D = Transform2D(
        xx = t xx this, xy = t xy this, xw = t xw this,
        yx = t yx this, yy = t yy this, yw = t yw this,
        tx = t tx this, ty = t ty this, tw = t tw this,
    )

    public companion object : Transformation.Builder<Transform2D> {
        public val IDENTITY: Transform2D = Transform2D(
            xx = 1.0, xy = 0.0, xw = 0.0,
            yx = 0.0, yy = 1.0, yw = 0.0,
            tx = 0.0, ty = 0.0, tw = 1.0,
        )

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Transform2D = Transform2D(
            xx = xx, xy = xy, xw = xw,
            yx = yx, yy = yy, yw = yw,
            tx = tx, ty = ty, tw = tw,
        )

        override fun toString(): String = "Transform2D"
    }
}
