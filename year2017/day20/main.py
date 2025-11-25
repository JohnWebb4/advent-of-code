import unittest
import re


class Vector3D:
    x: int
    y: int
    z: int

    def __init__(self, x: int, y: int, z: int):
        self.x = x
        self.y = y
        self.z = z

    def __str__(self):
        return f"Vector3D:({self.x},{self.y},{self.z})"

    def manhattan_distance(self) -> int:
        return abs(self.x) + abs(self.y) + abs(self.z)
    
    def add(vector_1, vector_2):
        return Vector3D(
            vector_1.x + vector_2.x,
            vector_1.y + vector_2.y,
            vector_1.z + vector_2.z
        )



class Particle:
    index: int
    position: Vector3D
    velocity: Vector3D
    acceleration: Vector3D

    def __init__(
        self, index: int, position: Vector3D, velocity: Vector3D, acceleration: Vector3D
    ):
        self.index = index
        self.position = position
        self.velocity = velocity
        self.acceleration = acceleration

    def __str__(self):
        return f"Particle:({self.index},{self.position},{self.velocity},{self.acceleration})"
    
    def update(self):
        self.velocity = Vector3D.add(self.velocity, self.acceleration)
        self.position = Vector3D.add(self.position, self.velocity)


def parse_particle(line: str, index: int) -> Particle:
    particle_regex = "p=<(\s?-?\d+),(-?\d+),(-?\d+)>, v=<(\s?-?\d+),(-?\d+),(-?\d+)>, a=<(\s?-?\d+),(-?\d+),(-?\d+)>"
    matches = re.match(particle_regex, line)
    groups = matches.groups()

    p_values = list(map(int, groups[0:3]))
    v_values = list(map(int, groups[3:6]))
    a_values = list(map(int, groups[6:9]))

    position = Vector3D(*p_values)
    velocity = Vector3D(*v_values)
    acceleration = Vector3D(*a_values)

    return Particle(index, position, velocity, acceleration)


def get_position_at_time(
    time: int, position: int, velocity: int, acceleration: int
) -> int:
    return 1 / 2 * acceleration * time * time + velocity * time + position


def get_vector_3d_at_time(time: int, particle: Particle) -> Vector3D:
    x = get_position_at_time(
        time, particle.position.x, particle.velocity.x, particle.acceleration.x
    )
    y = get_position_at_time(
        time, particle.position.y, particle.velocity.y, particle.acceleration.y
    )
    z = get_position_at_time(
        time, particle.position.z, particle.velocity.z, particle.acceleration.z
    )

    return Vector3D(x, y, z)


def get_long_term_closest_particle(input_data: str) -> int:
    particles = [
        parse_particle(line, index)
        for index, line in enumerate(input_data.splitlines())
    ]
    particles.sort(
        key=lambda particle: get_vector_3d_at_time(
            1_000_000_000_000, particle
        ).manhattan_distance()
    )

    return particles[0].index


def get_particles_left_after_collision(input_data: str) -> int:
    particles = [
        parse_particle(line, index)
        for index, line in enumerate(input_data.splitlines())
    ]

    for t in range(0, 1_000):
        collisions = {}
        for particle in particles:
            particle.update()

            try:
                collisions[str(particle.position)].append(particle)
            except KeyError:
                collisions[str(particle.position)] = [particle]

        for collision_position in collisions:
            collided_particles = collisions[collision_position]

            if len(collided_particles) > 1:
                for collided_particle in collided_particles:
                    particles.remove(collided_particle)

    return len(particles)


class Year2017Day20(unittest.TestCase):
    TEST_CASE_1: str = """p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"""

    TEST_CASE_2: str = """p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>    
p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>
p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>
p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"""

    def test_part_1(self):
        self.assertEqual(
            get_long_term_closest_particle(self.TEST_CASE_1), 0, "Test part 1 case 1"
        )

        with open("input.txt") as file:
            self.assertEqual(
                get_long_term_closest_particle(file.read()), 170, "Test part 1 input"
            )

        self.assertEqual(get_particles_left_after_collision(self.TEST_CASE_2), 1, "Test part 2 case 1")

        with open("input.txt") as file:
            # 525 is too low
            # 528 is too low
            self.assertEqual(
                get_particles_left_after_collision(file.read()), 571, "Test part 2 input"
            )


if __name__ == "__main__":
    unittest.main()
