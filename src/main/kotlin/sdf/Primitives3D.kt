package geomx.sdf

import geomx.transform.Vector2D
import geomx.transform.Vector3D
import mathx.length
import kotlin.math.*

public fun sphere(p: Vector3D, s: Double): Double {
    return p.length() - s
}

public fun box(p: Vector3D, b: Vector3D): Double {
    val q = abs(p) - b
    return max(q, 0.0).length() + min(max(q.x, max(q.y, q.z)), 0.0)
}

public fun boxRound(p: Vector3D, b: Vector3D, r: Double): Double {
    val q = abs(p) - b
    return max(q, 0.0).length() + min(max(q.x, max(q.y, q.z)), 0.0) - r
}

public fun boxFrame(p: Vector3D, b: Vector3D, e: Double): Double {
    val p1 = abs(p) - b
    val q = abs(p1 + e) - e
    return min(
        min(
            max(Vector3D(p1.x, q.y, q.z), 0.0).length() + min(max(p1.x, max(q.y, q.z)), 0.0),
            max(Vector3D(q.x, p1.y, q.z), 0.0).length() + min(max(q.x, max(p1.y, q.z)), 0.0),
        ),
        max(Vector3D(q.x, q.y, p1.z), 0.0).length() + min(max(q.x, max(q.y, p1.z)), 0.0),
    )
}

public fun torus(p: Vector3D, t: Vector2D): Double {
    val q = Vector2D(length(p.x, p.z) - t.x, p.y)
    return q.length() - t.y
}

public fun torusCapped(p: Vector3D, sc: Vector2D, ra: Double, rb: Double): Double {
    val p1 = Vector3D(abs(p.x), p.y, p.z)
    val k = if (sc.y * p1.x > sc.x * p1.y) Vector2D(p1.x, p1.y) dot sc else length(p1.x, p1.y)
    return sqrt((p1 dot p1) + ra * ra - 2.0 * ra * k) - rb
}

public fun link(p: Vector3D, le: Double, r1: Double, r2: Double): Double {
    val q = Vector3D(p.x, max(abs(p.y) - le, 0.0), p.z)
    return Vector2D(length(q.x, q.y) - r1, q.z).length() - r2
}

public fun cylinderInfinite(p: Vector3D, c: Vector3D): Double {
    return length(p.x - c.x, p.z - c.y) - c.z
}

public fun cone(p: Vector3D, c: Vector2D, h: Double): Double {
    // c is the sin/cos of the angle, h is height
    // Alternatively pass q instead of (c,h),
    // which is the point at the base in 2D
    val q = Vector2D(c.x / c.y * h, -h)
    val w = Vector2D(length(p.x, p.z), p.y)
    val a = w - q * ((w dot q) / (q dot q)).coerceIn(0.0, 1.0)
    val b = w - q * Vector2D((w.x / q.x).coerceIn(0.0, 1.0), 1.0)
    val k = sign(q.y)
    val d = min((a dot a), (b dot b))
    val s = max(k * (w.x * q.y - w.y * q.x), k * (w.y - q.y))
    return sqrt(d) * sign(s)
}

public fun coneApprox(p: Vector3D, c: Vector2D, h: Double): Double {
    val q = length(p.x, p.z)
    return max(c dot Vector2D(q, p.y), -h - p.y)
}

public fun coneInfinite(p: Vector3D, c: Vector2D): Double {
    // c is the sin/cos of the angle
    val q = Vector2D(length(p.x, p.z), -p.y)
    val d = (q - c * max((q dot c), 0.0)).length()
    return (d * if (q.x * c.y - q.y * c.x < 0.0) -1.0 else 1.0)
}

public fun plane(p: Vector3D, n: Vector3D, h: Double): Double {
    // n must be normalized
    return (p dot n) + h
}

public fun hexPrism(p: Vector3D, h: Vector2D): Double {
    val k = Vector3D(-0.8660254, 0.5, 0.57735)
    val kxy = Vector2D(k.x, k.y)
    val p1 = abs(p)
    val pp = kxy * (2.0 * min((kxy dot Vector2D(p1.x, p1.y)), 0.0))
    val p2 = Vector3D(pp.x, pp.y, p1.z)
    val d = Vector2D(
        (Vector2D(p2.x, p2.y) - Vector2D(p2.x.coerceIn(-k.z * h.x, k.z * h.x), h.x)).length() * sign(p2.y - h.x),
        p2.z - h.y,
    )
    return min(max(d.x, d.y), 0.0) + max(d, 0.0).length()
}

public fun triPrism(p: Vector3D, h: Vector2D): Double {
    val q = abs(p)
    return max(q.z - h.y, max(q.x * 0.866025 + p.y * 0.5, -p.y) - h.x * 0.5)
}

public fun capsule(p: Vector3D, a: Vector3D, b: Vector3D, r: Double): Double {
    val pa = p - a
    val ba = b - a
    val h = ((pa dot ba) / (ba dot ba)).coerceIn(0.0, 1.0)
    return (pa - ba * h).length() - r
}

public fun capsuleVertical(p: Vector3D, h: Double, r: Double): Double {
    val p1 = Vector3D(p.x, p.y.coerceIn(0.0, h), p.z)
    return p1.length() - r
}

public fun cylinderVertical(p: Vector3D, h: Double, r: Double): Double {
    val d = abs(Vector2D(length(p.x, p.z), p.y)) - Vector2D(r, h)
    return min(max(d.x, d.y), 0.0) + max(d, 0.0).length()
}

public fun cylinder(p: Vector3D, a: Vector3D, b: Vector3D, r: Double): Double {
    val ba = b - a
    val pa = p - a
    val baba = ba dot ba
    val paba = pa dot ba
    val x = (pa * baba - ba * paba).length() - r * baba
    val y = abs(paba - baba * 0.5) - baba * 0.5
    val x2 = x * x
    val y2 = y * y * baba
    val d = if (max(x, y) < 0.0) -min(x2, y2) else (if (x > 0.0) x2 else 0.0) + if (y > 0.0) y2 else 0.0
    return sign(d) * sqrt(abs(d)) / baba
}

public fun cylinderRound(p: Vector3D, ra: Double, rb: Double, h: Double): Double {
    val d = Vector2D(length(p.x, p.z) - 2.0 * ra + rb, abs(p.y) - h)
    return min(max(d.x, d.y), 0.0) + max(d, 0.0).length() - rb
}

public fun sdCappedCone(p: Vector3D, h: Double, r1: Double, r2: Double): Double {
    val q = Vector2D(length(p.x, p.z), p.y)
    val k1 = Vector2D(r2, h)
    val k2 = Vector2D(r2 - r1, 2.0 * h)
    val ca = Vector2D(q.x - min(q.x, if (q.y < 0.0) r1 else r2), abs(q.y) - h)
    val cb = q - k1 + k2 * ((k1 - q dot k2) / (k2 dot k2)).coerceIn(0.0, 1.0)
    val s = if (cb.x < 0.0 && ca.y < 0.0) -1.0 else 1.0
    return s * sqrt(min(ca dot ca, cb dot cb))
}

public fun coneCapped(p: Vector3D, a: Vector3D, b: Vector3D, ra: Double, rb: Double): Double {
    val rba = rb - ra
    val ba = b - a
    val baba = ba dot ba
    val pa = p - a
    val papa = pa dot pa
    val paba = (pa dot ba) / baba
    val x = sqrt(papa - paba * paba * baba)
    val cax = max(0.0, x - if (paba < 0.5) ra else rb)
    val cay = (abs(paba - 0.5) - 0.5).toFloat()
    val k = rba * rba + baba
    val f = ((rba * (x - ra) + paba * baba) / k).coerceIn(0.0, 1.0)
    val cbx = x - ra - f * rba
    val cby = paba - f
    val s = if (cbx < 0.0 && cay < 0.0) -1.0 else 1.0
    return s * sqrt(
        min(
            cax * cax + cay * cay * baba,
            cbx * cbx + cby * cby * baba,
        )
    )
}

public fun solidAngle(p: Vector3D, c: Vector2D, ra: Double): Double {
    // c is the sin/cos of the angle
    val q = Vector2D(length(p.x, p.z), p.y)
    val l = q.length() - ra
    val m = (q - c * (q dot c).coerceIn(0.0, ra)).length()
    return max(l, m * sign(c.y * q.x - c.x * q.y))
}

public fun sphereCut(p: Vector3D, r: Double, h: Double): Double {
    // sampling independent computations (only depend on shape)
    val w = sqrt(r * r - h * h)

    // sampling dependant computations
    val q = Vector2D(length(p.x, p.z), p.y)
    val s = max((h - r) * q.x * q.x + w * w * (h + r - 2.0 * q.y), h * q.x - w * q.y)
    return if (s < 0.0) q.length() - r else if (q.x < w) h - q.y else (q - Vector2D(w, h)).length()
}

public fun sphereHollowCut(p: Vector3D, r: Double, h: Double, t: Double): Double {
    // sampling independent computations (only depend on shape)
    val w = sqrt(r * r - h * h)

    // sampling dependant computations
    val q = Vector2D(length(p.x, p.z), p.y)
    return (if (h * q.x < w * q.y) (q - Vector2D(w, h)).length() else abs(q.length() - r)) - t
}

public fun deathStar(p2: Vector3D, ra: Double, rb: Double, d: Double): Double {
    // sampling independent computations (only depend on shape)
    val a = (ra * ra - rb * rb + d * d) / (2.0 * d)
    val b = sqrt(max(ra * ra - a * a, 0.0))

    // sampling dependant computations
    val p = Vector2D(p2.x, length(p2.y, p2.z))
    return if (p.x * b - p.y * a > d * max(b - p.y, 0.0)) (p - Vector2D(a, b)).length()
    else max(p.length() - ra, -((p - Vector2D(d, 0.0)).length() - rb))
}

public fun coneRound(p: Vector3D, r1: Double, r2: Double, h: Double): Double {
    // sampling independent computations (only depend on shape)
    val b = (r1 - r2) / h
    val a = sqrt(1.0 - b * b)

    // sampling dependant computations
    val q = Vector2D(length(p.x, p.z), p.y)
    val k = q dot Vector2D(-b, a)
    if (k < 0.0) return q.length() - r1
    return if (k > a * h) (q - Vector2D(0.0, h)).length() - r2 else (q dot Vector2D(a, b)) - r1
}

public fun coneRound(p: Vector3D, a: Vector3D, b: Vector3D, r1: Double, r2: Double): Double {
    // sampling independent computations (only depend on shape)
    val ba = b - a
    val l2 = ba dot ba
    val rr = r1 - r2
    val a2 = l2 - rr * rr
    val il2 = 1.0 / l2

    // sampling dependant computations
    val pa = p - a
    val y = pa dot ba
    val z = y - l2
    val x2 = dot2(pa * l2 - ba * y)
    val y2 = y * y * l2
    val z2 = z * z * l2

    // single square root!
    val k = sign(rr) * rr * rr * x2
    if (sign(z) * a2 * z2 > k) return sqrt(x2 + z2) * il2 - r2
    return if (sign(y) * a2 * y2 < k) sqrt(x2 + y2) * il2 - r1 else (sqrt(x2 * a2 * il2) + y * rr) * il2 - r1
}

public fun ellipsoid(p: Vector3D, r: Vector3D): Double {
    val k0 = (p / r).length()
    val k1 = (p / (r * r)).length()
    return k0 * (k0 - 1.0) / k1
}

public fun vesicaSegment(p: Vector3D, a: Vector3D, b: Vector3D, w: Double): Double {
    val c = (a + b) * 0.5
    val l = (b - a).length()
    val v = (b - a) / l
    val y = p - c dot v
    val q = Vector2D((p - c - v * y).length(), abs(y))

    val r = 0.5 * l
    val d = 0.5 * (r * r - w * w) / w
    val h = if (r * q.x < d * (q.y - r)) Vector3D(0.0, r, 0.0) else Vector3D(-d, 0.0, d + w)

    return (q - Vector2D(h.x, h.y)).length() - h.z
}

public fun rhombus(p: Vector3D, la: Double, lb: Double, h: Double, ra: Double): Double {
    val p1 = abs(p)
    val b = Vector2D(la, lb)
    val f = ((b ndot b - Vector2D(p1.x, p1.z) * 2.0) / (b dot b)).coerceIn(-1.0, 1.0)
    val q =
        Vector2D(
            (Vector2D(p1.x, p1.z) - b * 0.5 * Vector2D(1.0 - f, 1.0 + f)).length() *
                    sign(p1.x * b.y + p1.z * b.x - b.x * b.y) - ra,
            p1.y - h,
        )
    return min(max(q.x, q.y), 0.0) + max(q, 0.0).length()
}

public fun octahedron(p: Vector3D, s: Double): Double {
    val p1 = abs(p)
    val m = p1.x + p1.y + p1.z - s
    val q = when {
        3.0 * p1.x < m -> p1
        3.0 * p1.y < m -> Vector3D(p1.y, p1.z, p1.x)
        3.0 * p1.z < m -> Vector3D(p1.z, p1.x, p1.y)
        else -> return m * 0.57735027
    }
    val k = (0.5 * (q.z - q.y + s)).coerceIn(0.0, s)
    return length(q.x, q.y - s + k, q.z - k)
}

public fun octahedronApprox(p: Vector3D, s: Double): Double {
    val p1 = abs(p)
    return (p1.x + p1.y + p1.z - s) * 0.57735027
}

public fun triangle(p: Vector3D, a: Vector3D, b: Vector3D, c: Vector3D): Double {
    val ba = b - a
    val pa = p - a
    val cb = c - b
    val pb = p - b
    val ac = a - c
    val pc = p - c
    val nor = ba cross ac
    return sqrt(
        if (sign(ba cross nor dot pa) +
            sign(cb cross nor dot pb) +
            sign(ac cross nor dot pc) < 2.0
        ) min(
            min(
                dot2(ba * ((ba dot pa) / (ba dot ba)).coerceIn(0.0, 1.0) - pa),
                dot2(cb * ((cb dot pb) / (cb dot cb)).coerceIn(0.0, 1.0) - pb)
            ),
            dot2(ac * ((ac dot pc) / (ac dot ac)).coerceIn(0.0, 1.0) - pc)
        ) else (nor dot pa) * (nor dot pa) / (nor dot nor)
    )
}

public fun quad(p: Vector3D, a: Vector3D, b: Vector3D, c: Vector3D, d: Vector3D): Double {
    val ba = b - a
    val pa = p - a
    val cb = c - b
    val pb = p - b
    val dc = d - c
    val pc = p - c
    val ad = a - d
    val pd = p - d
    val nor = ba cross ad
    return sqrt(
        if (sign(ba cross nor dot pa) +
            sign(cb cross nor dot pb) +
            sign(dc cross nor dot pc) +
            sign(ad cross nor dot pd) < 3.0
        ) min(
            min(
                min(
                    dot2(ba * ((ba dot pa) / (ba dot ba)).coerceIn(0.0, 1.0) - pa),
                    dot2(cb * ((cb dot pb) / (cb dot cb)).coerceIn(0.0, 1.0) - pb)
                ),
                dot2(dc * ((dc dot pc) / (dc dot dc)).coerceIn(0.0, 1.0) - pc)
            ),
            dot2(ad * ((ad dot pd) / (ad dot ad)).coerceIn(0.0, 1.0) - pd)
        ) else (nor dot pa) * (nor dot pa) / (nor dot nor)
    )
}
