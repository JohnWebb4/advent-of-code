pub fn get_heat_loss(input: &str) -> u32 {
    0
}

#[cfg(test)]
mod test {
    use crate::get_heat_loss;

    const TEST_INPUT_1: &str = "2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533";

    #[test]
    fn test_heat_loss() {
        assert_eq!(102, get_heat_loss(TEST_INPUT_1));
    }
}
