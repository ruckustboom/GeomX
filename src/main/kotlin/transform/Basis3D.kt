package geomx.transform

import mathx.lerp

public data class Basis3D(
    override val xx: Double, override val xy: Double, override val xz: Double,
    override val yx: Double, override val yy: Double, override val yz: Double,
    override val zx: Double, override val zy: Double, override val zz: Double,
) : Transformation<Basis3D> {
    public fun interpolate(b: Basis3D, t: Double): Basis3D = Basis3D(
        xx = lerp(xx, b.xx, t), xy = lerp(xy, b.xy, t), xz = lerp(xz, b.xz, t),
        yx = lerp(yx, b.yx, t), yy = lerp(yy, b.yy, t), yz = lerp(yz, b.yz, t),
        zx = lerp(zx, b.zx, t), zy = lerp(zy, b.zy, t), zz = lerp(zz, b.zz, t),
    )

    override fun transformBy(t: Transformation<*>): Basis3D = Basis3D(
        xx = t xx this, xy = t xy this, xz = t xz this,
        yx = t yx this, yy = t yy this, yz = t yz this,
        zx = t zx this, zy = t zy this, zz = t zz this,
    )

    public companion object : Transformation.Builder<Basis3D> {
        public val IDENTITY: Basis3D = Basis3D(
            xx = 1.0, xy = 0.0, xz = 0.0,
            yx = 0.0, yy = 1.0, yz = 0.0,
            zx = 0.0, zy = 0.0, zz = 1.0,
        )

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Basis3D = Basis3D(
            xx = xx, xy = xy, xz = xz,
            yx = yx, yy = yy, yz = yz,
            zx = zx, zy = zy, zz = zz,
        )

        override fun toString(): String = "Basis3D"
    }
}
