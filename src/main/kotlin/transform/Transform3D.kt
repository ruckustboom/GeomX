package geomx.transform

import mathx.lerp

public data class Transform3D(
    override val xx: Double, override val xy: Double, override val xz: Double, override val xw: Double,
    override val yx: Double, override val yy: Double, override val yz: Double, override val yw: Double,
    override val zx: Double, override val zy: Double, override val zz: Double, override val zw: Double,
    override val tx: Double, override val ty: Double, override val tz: Double, override val tw: Double,
) : Transformation<Transform3D> {
    public fun interpolate(b: Transform3D, t: Double): Transform3D = Transform3D(
        xx = lerp(xx, b.xx, t), xy = lerp(xy, b.xy, t), xz = lerp(xz, b.xz, t), xw = lerp(xw, b.xw, t),
        yx = lerp(yx, b.yx, t), yy = lerp(yy, b.yy, t), yz = lerp(yz, b.yz, t), yw = lerp(yw, b.yw, t),
        zx = lerp(zx, b.zx, t), zy = lerp(zy, b.zy, t), zz = lerp(zz, b.zz, t), zw = lerp(zw, b.zw, t),
        tx = lerp(tx, b.tx, t), ty = lerp(ty, b.ty, t), tz = lerp(tz, b.tz, t), tw = lerp(tw, b.tw, t),
    )

    override fun transformBy(t: Transformation<*>): Transform3D = Transform3D(
        xx = t xx this, xy = t xy this, xz = t xz this, xw = t xw this,
        yx = t yx this, yy = t yy this, yz = t yz this, yw = t yw this,
        zx = t zx this, zy = t zy this, zz = t zz this, zw = t zw this,
        tx = t tx this, ty = t ty this, tz = t tz this, tw = t tw this,
    )

    public companion object : Transformation.Builder<Transform3D> {
        public val IDENTITY: Transform3D = Transform3D(
            xx = 1.0, xy = 0.0, xz = 0.0, xw = 0.0,
            yx = 0.0, yy = 1.0, yz = 0.0, yw = 0.0,
            zx = 0.0, zy = 0.0, zz = 1.0, zw = 0.0,
            tx = 0.0, ty = 0.0, tz = 0.0, tw = 1.0,
        )

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Transform3D = Transform3D(
            xx = xx, xy = xy, xz = xz, xw = xw,
            yx = yx, yy = yy, yz = yz, yw = yw,
            zx = zx, zy = zy, zz = zz, zw = zw,
            tx = tx, ty = ty, tz = tz, tw = tw,
        )

        override fun toString(): String = "Transform3D"
    }
}
