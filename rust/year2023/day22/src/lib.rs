pub fn get_safetly_disintegrated(input: &str) -> u32 {
    0
}

#[cfg(test)]
mod test {
    use crate::get_safetly_disintegrated;

    pub const TEST_INPUT_1: &str = "1,0,1~1,2,1
0,0,2~2,0,2
0,2,3~2,2,3
0,0,4~0,2,4
2,0,5~2,2,5
0,1,6~2,1,6
1,1,8~1,1,9";

    #[test]
    fn test_get_safetly_disintegrated() {
        assert_eq!(5, get_safetly_disintegrated(TEST_INPUT_1));
    }
}
