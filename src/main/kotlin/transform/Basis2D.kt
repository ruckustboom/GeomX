package geomx.transform

import mathx.lerp

public data class Basis2D(
    override val xx: Double, override val xy: Double,
    override val yx: Double, override val yy: Double,
) : Transformation<Basis2D> {
    public fun interpolate(b: Basis2D, t: Double): Basis2D = Basis2D(
        xx = lerp(xx, b.xx, t), xy = lerp(xy, b.xy, t),
        yx = lerp(yx, b.yx, t), yy = lerp(yy, b.yy, t),
    )

    override fun transformBy(t: Transformation<*>): Basis2D = Basis2D(
        xx = t xx this, xy = t xy this,
        yx = t yx this, yy = t yy this,
    )

    public companion object : Transformation.Builder<Basis2D> {
        public val IDENTITY: Basis2D = Basis2D(
            xx = 1.0, xy = 0.0,
            yx = 0.0, yy = 1.0,
        )

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Basis2D = Basis2D(
            xx = xx, xy = xy,
            yx = yx, yy = yy,
        )

        override fun toString(): String = "Basis2D"
    }
}
