/* Licensed under Apache-2.0 */
package advent.year2016.day7;

import java.util.HashSet;
import java.util.Set;

public class InternetProtocol {
    public static int getCountIPsSupportTLS(String input) {
        String[] ips = input.split("\n");
        int count = 0;

        for (String ip : ips) {
            if (doesIPSupportTLS(ip)) {
                count++;
            }
        }

        return count;
    }

    public static boolean doesIPSupportTLS(String ip) {
        boolean hasAbba = false;
        boolean hasAbbaInHyper = false;
        boolean isInHyper = false;

        for (int i = 0; i < ip.length() - 3; i++) {
            switch (ip.charAt(i)) {
                case '[':
                    isInHyper = true;
                    break;
                case ']':
                    isInHyper = false;
                    break;
                default:
                    break;
            }

            String sequence = ip.substring(i, i + 4);

            if (isAbba(sequence)) {
                if (isInHyper) {
                    hasAbbaInHyper = true;
                } else {
                    hasAbba = true;
                }

            }
        }
        return hasAbba && !hasAbbaInHyper;
    }

    public static boolean isAbba(String sequence) {
        if (sequence.charAt(0) == sequence.charAt(3) && sequence.charAt(1) == sequence.charAt(2) && sequence.charAt(0) != sequence.charAt(1)) {
            return true;
        }

        return false;
    }

    public static int getCountSupportSSL(String input) {
        String[] ips = input.split("\n");
        int count = 0;

        for (String ip : ips) {
            if (doesIPSupportSSL(ip)) {
                count++;
            }
        }

        return count;
    }

    public static boolean doesIPSupportSSL(String ip) {
        Set<String> superNetAba = new HashSet<>();
        Set<String> hypernetAba = new HashSet<>();
        boolean isHyper = false;

        for (int i = 0; i < ip.length() - 2; i++) {
            switch (ip.charAt(i)) {
                case '[':
                    isHyper = true;
                    break;
                case ']':
                    isHyper = false;
                default:
                    break;
            }

            String sequence = ip.substring(i, i + 3);

            if (isAba(sequence)) {
                if (isHyper) {
                    hypernetAba.add(sequence);
                } else {
                    superNetAba.add(sequence);
                }
            }
        }

        for (String aba : superNetAba) {
            String bab = "" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1);
            if (hypernetAba.contains(bab)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAba(String sequence) {
        if (sequence.charAt(0) == sequence.charAt(2) && sequence.charAt(0) != sequence.charAt(1)) {
            return true;
        }

        return false;
    }
}
