document.addEventListener('DOMContentLoaded', () => {
    const destinations = [
        {
            link: "https://tpwd.texas.gov/state-parks/enchanted-rock",
            name: "Enchanted Rock",
            image: "https://cdn1.socalhiker.net/wp-content/uploads/2014/03/01064110/enchanted-rock.jpg",
            description: "Hike up this massive pink granite dome for stunning views and natural beauty, just an hour from Austin."
        },
        {
            link: "https://www.mountbonnell.info/",
            name: "Mount Bonnell",
            image: "https://media.tacdn.com/media/attractions-splice-spp-674x446/12/e7/21/22.jpg",
            description: "Enjoy panoramic views of the Colorado River and Austin skyline from this historic scenic spot."
        },
        {
            link:"https://parks.traviscountytx.gov/parks/hamilton-pool-preserve",
            name: "Hamilton Pool",
            image: "https://austin.culturemap.com/media-library/hamilton-pool.png?id=26967277&width=2000&height=1500&quality=65&coordinates=0%2C0%2C0%2C0",
            description: "Swim in this breathtaking natural pool with a 50-foot waterfall, a short drive from Austin."
        },
        {
            link:"https://www.austintexas.org/austin-insider-blog/post/your-guide-to-paddling-lady-bird-lake/",
            name: "Lady Bird Lake",
            image: "https://assets.simpleviewinc.com/simpleview/image/upload/c_fill,h_800,q_75,w_1000/v1/clients/austin/temp_16f03121-8190-4c7b-8c18-d07113f1bdf1.jpg",
            description: "Kayak, paddleboard, or stroll along this scenic reservoir right in the heart of Austin."
        }
    ];

    const container = document.getElementById('destinations-container');

    function createDestinationCard(destination) {
        const card = document.createElement('div');
        card.classList.add('destination-card');

        card.innerHTML = `
            
            <img src="${destination.image}" alt="${destination.name}">
            <h2><a href="${destination.link}">${destination.name} </a></h2>
            <p>${destination.description}</p>
        `;

        container.appendChild(card);
    }

    // Loop through the destinations array and generate cards
    destinations.forEach(destination => createDestinationCard(destination));
});
