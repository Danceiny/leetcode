package cc.cannot.algorithms.ipv4_to_int;

/**
 * Programming Question:
 * Convert an IPv4 address in the format of null-terminated C string into a 32-bit integer.
 * For example, given an IP address “172.168.5.1”, the output should be a 32-bit integer
 * with “172” as the highest order 8 bit, 168 as the second highest order 8 bit, 5 as the
 * second lowest order 8 bit, and 1 as the lowest order 8 bit. That is,
 * "172.168.5.1" => 2896692481
 * Requirements:
 * 1. You can only iterate the string once.
 * 2. You should handle spaces correctly: a string with spaces between a digit and a dot is
 * a valid input; while a string with spaces between two digits is not.
 * "172[Space].[Space]168.5.1" is a valid input. Should process the output normally.
 * "1[Space]72.168.5.1" is not a valid input. Should report an error.
 * 3. Please provide unit tests.
 */
public class Ipv4ToInt32 {
    /**
     * iter string backward (assume that we can get length of the string without iteration)
     *
     * @param ipv4
     * @return
     */
    public static int sol_v1(String ipv4) {
        int l = ipv4.length();
        int ret = 0;
        int groupIndex = 0; // "172.168.5.1" has 4 groups: ["172", "168","5","1"]
        int ii = 0; // char index in one group
        char preChar = '0';
        int groupBits = 0; // store group value like 172, in the view of binary, groupBits is like unsigned int8
        for (int i = l - 1; i >= 0; i--) {
            char c = ipv4.charAt(i);
            if (c == '.') {
                if (++groupIndex > 3) {
                    throw new RuntimeException("ip error");
                }
                ret |= (groupBits << (8 * (groupIndex - 1)));
                ii = 0;
                groupBits = 0;

            } else if (preChar == ' ' && c != ' ' && ii != 0) {
                throw new RuntimeException("space error");
            } else if (c != ' ') {
                int v = c - 48;
                int vv = v * pow(10, ii);
                groupBits += vv;
                if (++ii > 3) {
                    throw new RuntimeException("ip error");
                }
                if (groupBits > 255) {
                    throw new RuntimeException("ip error");
                }
            }
            preChar = c;
        }
        if (groupIndex != 3) {
            throw new RuntimeException("ip error");
        }
        ret |= (groupBits << (8 * groupIndex));
        return ret;
    }

    /**
     * iter string from start to end (assume that string ends with '\0')
     *
     * @param ipv4
     * @return
     */
    public static int sol_v2(String ipv4) {
        int i = 0;
        int ret = 0;
        int groupIndex = 0; // "172.168.5.1" has 4 groups: ["172", "168","5","1"]
        int ii = 0; // char index in one group
        char preChar = '0';
        int groupBits = 0; // store group value like 172, in the view of binary, groupBits is like unsigned int8
        while (true) {
            char c = ipv4.charAt(i++);
            if (c == '\0') {
                break;
            }
            if (c == '.') {
                if (++groupIndex > 3) {
                    throw new RuntimeException("ip error");
                }
                ret |= (groupBits << (8 * (4 - groupIndex)));
                ii = 0;
                groupBits = 0;
            } else if (preChar == ' ' && c != ' ' && ii != 0) {
                throw new RuntimeException("space error");
            } else if (c != ' ') {
                int v = c - 48;
                groupBits = groupBits * 10 + v;
                if (++ii > 3) {
                    throw new RuntimeException("ip error");
                }
                if (groupBits > 255) {
                    throw new RuntimeException("ip error");
                }
            }
            preChar = c;
        }
        if (groupIndex != 3) {
            throw new RuntimeException("ip error");
        }
        ret |= (groupBits << (8 * (3 - groupIndex)));
        return ret;
    }

    public static int pow(int v, int m) {
        if (m == 0) {
            return 1;
        }
        while (--m > 0) {
            v *= v;
        }
        return v;
    }

    public static String int2bits(int v) {
        String s = String.format("%32s", Integer.toBinaryString(v)).replace(' ', '0');
        return formatLongBitsStr(s);
    }

    public static String long2bits(long v) {
        String s = String.format("%64s", Long.toBinaryString(v)).replace(' ', '0');
        return formatLongBitsStr(s);
    }

    private static String formatLongBitsStr(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = s.length(); i < l; i++) {
            if (i > 0 && i % 8 == 0) {
                sb.append('_').append(s.charAt(i));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }


    // we use the raw binary of java int value to represent unsigned int，now we need to convert it to java long，
    // so that we can print it as expected
    private static long unsignedintToLong(int v) {
        return v & 0xffffffffL;
    }

    private static void test_sol(int version) {
        String ip; // ip string (with '\0' or not)
        int a; // ip string => binary int
        String errmsg = ""; // detect error

        if (version == 1) {
            // System.out.println("测试逆向遍历算法");
            ip = "172.168.5.1";
            a = sol_v1(ip);
            assert unsignedintToLong(a) == 2896692481L;
            ip = "172 . 168.5.1";
            a = sol_v1(ip);
            assert unsignedintToLong(a) == 2896692481L;

            try {
                ip = "1 72.168.5.1";
                sol_v1(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "space error".equals(errmsg);

            try {
                ip = "192.268.5.1";
                sol_v1(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.fff.5.1";
                sol_v1(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.00000.5.1";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.00000";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.1.1.1.1";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

        } else if (version == 2) {
            ip = "172.168.5.1\0";
            // System.out.println("测试正向遍历算法");
            a = sol_v2(ip);
            assert unsignedintToLong(a) == 2896692481L;
            ip = "172 . 168.5.1\0";
            a = sol_v2(ip);
            assert unsignedintToLong(a) == 2896692481L;

            try {
                ip = "1 72.168.5.1\0";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "space error".equals(errmsg);

            try {
                ip = "192.268.5.1\0";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.fff.5.1\0";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.00000.5.1\0";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.000\0";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);

            try {
                ip = "192.1.1.1.1\0";
                sol_v2(ip);
            } catch (Exception e) {
                errmsg = e.getMessage();
            }
            assert "ip error".equals(errmsg);
        }
    }


    public static void main(String[] args) {
        test_sol(1);

        test_sol(2);
        long start, end;

        // 1,0000,0000 times calculation of correct ip input, cost about 3000ms
        // Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
        // processor	: 5
        // vendor_id	: GenuineIntel
        // cpu family	: 6
        // model		: 142
        // model name	: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
        // stepping	: 10
        // microcode	: 0x84
        // cpu MHz		: 3225.984
        // cache size	: 8192 KB
        // physical id	: 0
        // siblings	: 8
        // core id		: 1
        // cpu cores	: 2
        // apicid		: 3
        // initial apicid	: 3
        // fpu		: yes
        // fpu_exception	: yes
        // cpuid level	: 22
        // wp		: yes
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            sol_v1("192.168.1.13");
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("逆向遍历算法耗时: %d ms", end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            sol_v2("192.168.1.13\0");
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("正向遍历算法耗时: %d ms", end - start));


        // we using unit test to cover exception conditions, the two algorithms are still very close
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            test_sol(2);
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("正向遍历算法耗时: %d ms", end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            test_sol(1);
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("逆向遍历算法耗时: %d ms", end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            test_sol(2);
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("正向遍历算法耗时: %d ms", end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            test_sol(1);
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("逆向遍历算法耗时: %d ms", end - start));
    }
}
