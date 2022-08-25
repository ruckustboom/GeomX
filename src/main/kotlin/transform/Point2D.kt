package geomx.transform

import mathx.length
import mathx.lerp

public data class Point2D(
    public val x: Double,
    public val y: Double,
) : Transformation<Point2D> {
    override val tx: Double get() = x
    override val ty: Double get() = y

    public operator fun plus(v: Point2D): Point2D = Point2D(x = x + v.x, y = y + v.y)
    public operator fun minus(v: Point2D): Point2D = Point2D(x = x - v.x, y = y - v.y)
    public operator fun times(s: Double): Point2D = Point2D(x = x * s, y = y * s)
    public operator fun div(s: Double): Point2D = Point2D(x = x / s, y = y / s)

    @JvmName("negate")
    public operator fun unaryMinus(): Point2D = Point2D(x = -x, y = -y)

    public fun distanceTo(p: Point2D): Double = length(x = p.x - x, y = p.y - y)

    public infix fun dot(v: Point2D): Double = x * v.x + y * v.y

    public fun interpolate(b: Point2D, t: Double): Point2D = Point2D(
        x = lerp(x, b.x, t),
        y = lerp(y, b.y, t),
    )

    override fun transformBy(t: Transformation<*>): Point2D = Point2D(
        x = t tx this,
        y = t ty this,
    )

    public companion object : Transformation.Builder<Point2D> {
        public val ZERO: Point2D = Point2D(x = 0.0, y = 0.0)
        public val X: Point2D = x()
        public val Y: Point2D = y()

        public val POSITIVE_INFINITY: Point2D = Point2D(
            x = Double.POSITIVE_INFINITY,
            y = Double.POSITIVE_INFINITY,
        )
        public val NEGATIVE_INFINITY: Point2D = Point2D(
            x = Double.NEGATIVE_INFINITY,
            y = Double.NEGATIVE_INFINITY,
        )
        public val NaN: Point2D = Point2D(
            x = Double.NaN,
            y = Double.NaN,
        )

        public fun x(x: Double = 1.0): Point2D = Point2D(x = x, y = 0.0)
        public fun y(y: Double = 1.0): Point2D = Point2D(x = 0.0, y = y)

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Point2D = Point2D(x = tx, y = ty)

        override fun toString(): String = "Vector3D"
    }
}
