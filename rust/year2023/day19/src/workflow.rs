use crate::rule::Rule;

pub struct Workflow {
    pub name: String,
    pub rules: Vec<Rule>,
    pub else_target: String,
}

pub fn new_workflow(name: String, rules: Vec<Rule>, else_target: String) -> Workflow {
    Workflow {
        name,
        rules,
        else_target,
    }
}
