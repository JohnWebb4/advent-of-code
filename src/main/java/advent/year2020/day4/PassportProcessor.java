/* Licensed under Apache-2.0 */
package advent.year2020.day4;

public class PassportProcessor {
	public static class Passport {
		int byr;
		int iyr;
		int eyr;
		String hgt;
		String hcl;
		String ecl;
		String pid;
		String cid;

		public Passport(int byr, int iyr, int eyr, String hgt, String hcl, String ecl, String pid, String cid) {
			this.byr = byr;
			this.iyr = iyr;
			this.eyr = eyr;
			this.hgt = hgt;
			this.hcl = hcl;
			this.ecl = ecl;
			this.pid = pid;
			this.cid = cid;
		}

		public static Passport parsePassport(String passport) {
			String[] fields = passport.split("[ \n]");

			int byr = 0;
			int iyr = 0;
			int eyr = 0;
			String hgt = "";
			String hcl = "";
			String ecl = "";
			String pid = "";
			String cid = "";

			for (String field : fields) {
				String[] keyValue = field.split(":");
				String key = keyValue[0];
				String value = keyValue[1];

				if (key.equals("byr")) {
					byr = Integer.parseInt(value);
				} else if (key.equals("iyr")) {
					iyr = Integer.parseInt(value);
				} else if (key.equals("eyr")) {
					eyr = Integer.parseInt(value);
				} else if (key.equals("hgt")) {
					hgt = value;
				} else if (key.equals("hcl")) {
					hcl = value;
				} else if (key.equals("ecl")) {
					ecl = value;
				} else if (key.equals("pid")) {
					pid = value;
				} else if (key.equals("cid")) {
					cid = value;
				}
			}

			if (byr >= 1920 && byr <= 2002) {
				if (iyr >= 2010 && iyr <= 2020) {
					if (eyr >= 2020 && eyr <= 2030) {
						if (hgt.matches("\\d+(cm|in)")) {
							int value = Integer.parseInt(hgt.split("(cm|in)")[0]);

							if (hgt.contains("cm")) {
								if (value < 150 || value > 193) {
									return null;
								}
							} else if (hgt.contains("in")) {
								if (value < 59 || value > 76) {
									return null;
								}
							}

							if (hcl.matches("#[a-f0-9]{6}")) {
								if (ecl.matches("(amb|blu|brn|gry|grn|hzl|oth)")) {
									if (pid.length() == 9) {
										return new Passport(byr, iyr, eyr, hgt, hcl, ecl, pid, cid);
									}
								}
							}
						}
					}
				}
			}

			return null;
		}
	}

	public static int countValidPassports(String input) {
		return countValidPassports(input.split("\n\n"));
	}

	public static int countValidPassports(String[] passports) {
		int countValid = 0;

		for (String passportInput : passports) {
			Passport passport = Passport.parsePassport(passportInput);

			if (passport != null) {
				countValid++;
			}
		}

		return countValid;
	}
}
