/* Licensed under Apache-2.0 */
package advent.year2015.day16;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AuntSue {
    public static Pattern auntPattern = Pattern.compile("^([\\w\\s]+):(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?(?:\\s?(\\w+: \\d+),?)?");
    public static Pattern fieldPattern = Pattern.compile("^(\\w+): (\\d+)$");

    public static class AuntAnalysis {
        public static class AuntAnalysisBuilder {
            String name;
            Integer children;
            Integer cats;
            Integer samoyeds;
            Integer pomeranians;
            Integer akitas;
            Integer vizslas;
            Integer goldfish;
            Integer trees;
            Integer cars;
            Integer perfumes;

            public AuntAnalysis build() {
                return new AuntAnalysis(name, children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes);
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setChildren(Integer children) {
                this.children = children;
            }

            public void setCats(Integer cats) {
                this.cats = cats;
            }

            public void setSamoyeds(Integer samoyeds) {
                this.samoyeds = samoyeds;
            }

            public void setPomeranians(Integer pomeranians) {
                this.pomeranians = pomeranians;
            }

            public void setAkitas(Integer akitas) {
                this.akitas = akitas;
            }

            public void setVizslas(Integer vizslas) {
                this.vizslas = vizslas;
            }

            public void setGoldfish(Integer goldfish) {
                this.goldfish = goldfish;
            }

            public void setTrees(Integer trees) {
                this.trees = trees;
            }

            public void setCars(Integer cars) {
                this.cars = cars;
            }

            public void setPerfumes(Integer perfumes) {
                this.perfumes = perfumes;
            }
        }

        public final String name;
        public final Integer children;
        public final Integer cats;
        public final Integer samoyeds;
        public final Integer pomeranians;
        public final Integer akitas;
        public final Integer vizslas;
        public final Integer goldfish;
        public final Integer trees;
        public final Integer cars;
        public final Integer perfumes;

        public AuntAnalysis(String name, Integer children, Integer cats, Integer samoyeds, Integer pomeranians, Integer akitas, Integer vizslas, Integer goldfish, Integer trees, Integer cars, Integer perfumes) {
            this.name = name;
            this.children = children;
            this.cats = cats;
            this.samoyeds = samoyeds;
            this.pomeranians = pomeranians;
            this.akitas = akitas;
            this.vizslas = vizslas;
            this.goldfish = goldfish;
            this.trees = trees;
            this.cars = cars;
            this.perfumes = perfumes;
        }

        public boolean equals(AuntAnalysis auntAnalysis) {
            if (this.children != null && auntAnalysis.children != null && !this.children.equals(auntAnalysis.children)) {
                return false;
            }

            if (this.cats != null && auntAnalysis.cats != null && this.cats.equals(auntAnalysis.cats)) {
                return false;
            }

            if (this.samoyeds != null && auntAnalysis.samoyeds != null && !this.samoyeds.equals(auntAnalysis.samoyeds)) {
                return false;
            }

            if (this.pomeranians != null && auntAnalysis.pomeranians != null && !this.pomeranians.equals(auntAnalysis.pomeranians)) {
                return false;
            }

            if (this.akitas != null && auntAnalysis.akitas != null && !this.akitas.equals(auntAnalysis.akitas)) {
                return false;
            }

            if (this.vizslas != null && auntAnalysis.vizslas != null && !this.vizslas.equals(auntAnalysis.vizslas)) {
                return false;
            }

            if (this.goldfish != null && auntAnalysis.goldfish != null && !this.goldfish.equals(auntAnalysis.goldfish)) {
                return false;
            }

            if (this.trees != null && auntAnalysis.trees != null && !this.trees.equals(auntAnalysis.trees)) {
                return false;
            }

            if (this.cars != null && auntAnalysis.cars != null && !this.cars.equals(auntAnalysis.cars)) {
                return false;
            }

            return this.perfumes == null || auntAnalysis.perfumes == null || this.perfumes.equals(auntAnalysis.perfumes);
        }

        public boolean equalsWithDecay(AuntAnalysis auntAnalysis) {
            if (this.children != null && auntAnalysis.children != null && !this.children.equals(auntAnalysis.children)) {
                return false;
            }

            if (this.cats != null && auntAnalysis.cats != null && this.cats > auntAnalysis.cats) {
                return false;
            }

            if (this.samoyeds != null && auntAnalysis.samoyeds != null && !this.samoyeds.equals(auntAnalysis.samoyeds)) {
                return false;
            }

            if (this.pomeranians != null && auntAnalysis.pomeranians != null && this.pomeranians < auntAnalysis.pomeranians) {
                return false;
            }

            if (this.akitas != null && auntAnalysis.akitas != null && !this.akitas.equals(auntAnalysis.akitas)) {
                return false;
            }

            if (this.vizslas != null && auntAnalysis.vizslas != null && !this.vizslas.equals(auntAnalysis.vizslas)) {
                return false;
            }

            if (this.goldfish != null && auntAnalysis.goldfish != null && this.goldfish < auntAnalysis.goldfish) {
                return false;
            }

            if (this.trees != null && auntAnalysis.trees != null && this.trees > auntAnalysis.trees) {
                return false;
            }

            if (this.cars != null && auntAnalysis.cars != null && !this.cars.equals(auntAnalysis.cars)) {
                return false;
            }

            return this.perfumes == null || auntAnalysis.perfumes == null || this.perfumes.equals(auntAnalysis.perfumes);
        }
    }

    public static int getIndexSueGotGift(String testResults, String auntSueStringAll) {
        String[] auntSueStrings = auntSueStringAll.split("\n");
        final AuntAnalysis testAunt = getAunt(testResults);

        List<AuntAnalysis> auntList = new LinkedList<AuntAnalysis>();

        for (String auntSueString : auntSueStrings) {
            auntList.add(getAunt(auntSueString));
        }

        boolean hasDeleted = true;
        do {
            List<AuntAnalysis> newList = auntList.stream().filter(testAunt::equals).collect(Collectors.toList());

            hasDeleted = newList.size() != auntList.size();
            auntList = newList;
        } while (hasDeleted);

        return Integer.parseInt(auntList.get(0).name.split(" ")[1]);

    }

    public static int getIndexSueGotGiftWithDecay(String testResults, String auntSueStringAll) {
        String[] auntSueStrings = auntSueStringAll.split("\n");
        final AuntAnalysis testAunt = getAunt(testResults);

        List<AuntAnalysis> auntList = new LinkedList<AuntAnalysis>();

        for (String auntSueString : auntSueStrings) {
            auntList.add(getAunt(auntSueString));
        }

        boolean hasDeleted = true;
        do {
            List<AuntAnalysis> newList = auntList.stream().filter(testAunt::equalsWithDecay).collect(Collectors.toList());

            hasDeleted = newList.size() != auntList.size();
            auntList = newList;
        } while (hasDeleted);

        return Integer.parseInt(auntList.get(1).name.split(" ")[1]);
    }

    public static AuntAnalysis getAunt(String input) {
        AuntAnalysis.AuntAnalysisBuilder builder = new AuntAnalysis.AuntAnalysisBuilder();

        Matcher auntMatcher = auntPattern.matcher(input);

        if (auntMatcher.find()) {
            builder.setName(auntMatcher.group(1));

            for (int i = 1; i < auntMatcher.groupCount(); i++) {
                String field = auntMatcher.group(i + 1);

                if (field == null) {
                    continue;
                }

                Matcher fieldMatcher = fieldPattern.matcher(field);

                if (fieldMatcher.find()) {
                    String fieldName = fieldMatcher.group(1);
                    Integer fieldValue = Integer.parseInt(fieldMatcher.group(2));

                    switch (fieldName) {
                        case "children":
                            builder.setChildren(fieldValue);
                            break;
                        case "cats":
                            builder.setCats(fieldValue);
                            break;
                        case "samoyeds":
                            builder.setSamoyeds(fieldValue);
                            break;
                        case "pomeranians":
                            builder.setPomeranians(fieldValue);
                            break;
                        case "akitas":
                            builder.setAkitas(fieldValue);
                            break;
                        case "vizslas":
                            builder.setVizslas(fieldValue);
                            break;
                        case "goldfish":
                            builder.setGoldfish(fieldValue);
                            break;
                        case "trees":
                            builder.setTrees(fieldValue);
                            break;
                        case "cars":
                            builder.setCars(fieldValue);
                            break;
                        case "perfumes":
                            builder.setPerfumes(fieldValue);
                            break;
                        default:
                            break;
                    }
                }
            }
        }


        return builder.build();
    }
}
