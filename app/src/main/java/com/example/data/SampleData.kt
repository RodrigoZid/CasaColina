package com.example.data

import com.example.model.Dish
import com.example.model.MenuCategory

object SampleData {
    const val HERO_BANNER_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuBE_HD6-hWo7ePrm3GtVLxNammO7a9v8afaBtUwDrJ9P8mEI_SqsAJhg_QvRxZ6nCg_t6lnc98p4MAFeOIwJiYhpSKbj8QRSZCwn8rTsdE3LB7fN3PbvaaXA0PlNPopJ8-z8cH2ky9TDwBdK-QmnJ4822jRcYz_Q6Zj6Nl9b_x5rB6hzaxNsnZszYeFipxumr_m1E3kL_KHm7r-LV_mM5KeXIQrLkFsHGdARa_-dLe4Wy7aFxys"
    const val PATIO_VIRREINAL_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuC65oA3R7wbrnxPsWdvDPRGv4_0oL-R8DUUIs5V7BI43bR-_N4hS4t74HoLcQ3PRSvN7l2ABeeBr2m1e9qnHd3oCOv2_t_bcEfbl-0YneNuh2HdZhhiIYsle6ihR49_Y8LfQitGNQ9ROw2Dim8CKPCGfoyJfBIhd-_ek17NDRzve-72dEZfeT4sOO4A3jnM5spdS_4jnsVie_pW9d_Y1Cmw1OcOnVu1p-Wz9x9hUK03Iayd_odQ"
    const val CAVA_DE_VINOS_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuAjYRzpRaUsxECY9cGaOGky6u4rWotBueu4qn_e7UjCo-lqGhC01eTHWctTFZf7ZJArUoL3CD1bSt1zyYR3k48PT5h7y3DzQI-D7OoLX59PkBmKBfSTal9WGKX0QO8QWK2zOVKq_UnNlGKYcqmADB2NGi7D3-koMauk0th7tt_2SovE-_aPD-A7cbM_ckmv_ZlVZDZhkdXLokrIE1p7fiYOR3SIrOjVuk0qL2LBX0ncMlWwDx3u"
    const val PROFILE_AVATAR_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuBcTfJg0THSxdJo8iUq8bM_Ew9ARZIwuD5i6ixAZVbGZyVjSRqmgUhr1KqLW5kg2k13nOabPQ5OI6JHO80hFTjd2eCbpXgw1225kfcdl5zaeYtvIaxnu1zRc1i2GYhDGUGGPEWXFDdovGiKi9vlkclDFxu_X1v2nsapXuSgzI-TuTpoN2O_xoRqVGt2K7R2A2r6bDoH94TJTrScvWs1R7ilE-SBeO4CA79WOqqo8ORGoRkvlJVU"

    val DISHES = listOf(
        Dish(
            id = "lomo",
            name = "Lomo Saltado al Wok",
            category = MenuCategory.FONDOS,
            price = 68.00,
            prepTime = "15 min",
            shortDescription = "Lomo fino, cebolla morada, tomate y papas nativas amarillas crocantes.",
            fullDescription = "Trozos jugosos de lomo fino flambeados al pisco en wok ahumado con gajos de cebolla roja crujiente, tomates frescos y ají amarillo. Acompañado de papas nativas crocantes y arroz con choclo tierno.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDhImrDdmjtqCqNlQ173Q3ALX1x231As9hqV3p13iBrJIVDH58-I84KPkRS2py8vgFQ021eGerLZBWNww02Cn0oG-aYQz9wND0bPMsDdHyDP_QC7t8CZwATjO0Ez_WkBQV4uaIOv6OWKQF4FPN4LheYG8IhP0d8Dz8LTRKlYuqy-naFfU0KFALfUXRCDiIIabsK1lhyDApbnOD9_uJ-wN1de979r9e_N2yJa32KxcIb4u_w9G9q",
            badge = "Top #1",
            isChefPick = true,
            rating = 4.9,
            reviewsCount = 340
        ),
        Dish(
            id = "pato",
            name = "Arroz con Pato Criollo",
            category = MenuCategory.FONDOS,
            price = 64.00,
            prepTime = "20 min",
            shortDescription = "Muslo tierno confitado, arroz norteño a la chicha de jora y culantro.",
            fullDescription = "Tierna pierna de pato macerada en chicha de jora y culantro criollo, cocinada a fuego lento sobre arroz norteño al punto de grano con pimientos rojos y choclo tierno.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDxpsz4v3l-80wZwkiISsf8qbSJ5ffUELra_3sIgCYO7bSXZf1eds8xwZxmEaVL6-8VlPCuxu0soTVdAZPKFuozhO7TUvumRr3fW2_y9KihXnsWarW2hc6ZYALYCS4qKNvlHeXfxYGww-t4bHAhjQp4_MAmOGtOf5QfxvA5OzocOP7Q3gB1Exi71tICar-lCIVhhQl0lV_gbT-5pTAmqQ725riE-el89SQz2hsGPWQkmezvIFCe",
            isChefPick = true,
            rating = 4.8,
            reviewsCount = 215
        ),
        Dish(
            id = "ceviche",
            name = "Ceviche Clásico Limeño",
            category = MenuCategory.ENTRADAS,
            price = 58.00,
            prepTime = "10 min",
            shortDescription = "Pesca del día, leche de tigre al ají limo, choclo desgranado y camote.",
            fullDescription = "Fresca pesca matutina del litoral peruano, marinada en jugo puro de limones norteños con ají limo, cebolla roja pluma, choclo desgranado y camote glaseado.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCbamj3vWc5BSAcvf_8kjb5x3bFvw5l61WN0H_L2t_9siBNpbOBDvIpA5UwZuzENcig75CT0cFdwG6DfJs4gIE0abWkjJu7GJObxFch0auGHiyHWYgwloMV8r69Gmp8FJlgnHbUtiu4I8c4izGB6OF2DQu3kLwKCkryE7oVWLEa6CI3AF6Hol9yus0oIxWG3SrwrWybnwPF_01Nlx1ysExWVt856aO0nEnIrzlBZHcrVQ1nga-9",
            badge = "Fresco",
            isChefPick = true,
            rating = 4.9,
            reviewsCount = 412
        ),
        Dish(
            id = "causa",
            name = "Causa Limeña Imperial",
            category = MenuCategory.ENTRADAS,
            price = 48.00,
            prepTime = "10 min",
            shortDescription = "Papa amarilla al ají limo, pulpa de cangrejo y palta fuerte de huerto.",
            fullDescription = "Suave terrina de papa amarilla prensada al ají amarillo y lima, rellena con generosa pulpa de cangrejo y palta fuerte de huerto orgánico, coronada con huevo de codorniz.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBF0FzyjFU4VJI12XBcNkohS89zf9AH3CmDEbsSgKZqaNjIgkwOaav10Eg2Nuv5qOhfIDsaECSrpohUNTynfYp0AU1O7XNqbqFz87ogPLrZjz5iw3eHLPCK2wDCA4GjyGr5mqoM01KEPc-0_U7Jfe57sJNOwGfx6eAKFRLd2t-pb-VXdRjbvYXT8yWSPk19UlzTLHCKZY9tYyKFnpHgkI_MQFWo2O5cP4WJWclWV5vn-dV8E4UM",
            rating = 4.7,
            reviewsCount = 189
        ),
        Dish(
            id = "aji",
            name = "Ají de Gallina Conventual",
            category = MenuCategory.FONDOS,
            price = 54.00,
            prepTime = "12 min",
            shortDescription = "Pechuga deshilachada en crema suave de ají amarillo, pecanas y queso paria.",
            fullDescription = "Receta tradicional de convento virreinal con pechuga deshilachada en crema sedosa de ají amarillo, queso paria andino y nueces tostadas, servida con aceituna botija y arroz blanco.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD4KgkfQJsITZAjUdGg_sp-MJbvfnuK0LZhjAEOtEJPJITey8pz7gjbXklT-jgVUO9ER3rMomcFmrDdLKhbq_SWNB5p5R0SlPDxHzrObr40kvmP-eD50c2Opcv-00wgB-FV-m78Ra9sgSwAMbwHYRUTMIWxV5j-heXc7HeTSJgUNF-CvkTpmpuBVZi6xZUYUwAj5quWm84r2JqsQXiqsH2Siv681tqmUqEHEdM2TT-x7ETFflm0",
            rating = 4.9,
            reviewsCount = 280
        ),
        Dish(
            id = "suspiro",
            name = "Suspiro a la Limeña",
            category = MenuCategory.POSTRES,
            price = 26.00,
            prepTime = "Inmediato",
            shortDescription = "Manjar blanco de yemas de corral y merengue al oporto perfumado con canela.",
            fullDescription = "Postre limeño de antaño servido en copa de cristal con base de manjar blanco aterciopelado de yemas de corral y merengue italiano al vino de oporto espolvoreado con canela fina.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAqK6-4IaKwZ9cZyZPOZxHSAeu9C7oRl0p7VLE_nNMlPpFC_F6dIlEDTJgXVrKqWWXGgIX86X638eye7G7DZOm1nOUWTOu3FND4pqcwmlclD8TozwNvfV7JalYHhPzFgDkuoc0ZMYnqJM_NHJBxn0oxlR5Pez3GsdCw1Fg8y-3XIvvaTVbn4rujytO4iT9xwYIKZlnhH6dyR1Kztd9atPW-AN8e5GZ9NRzfKjEVbEzVuyQ_vDYL",
            rating = 4.9,
            reviewsCount = 310
        ),
        Dish(
            id = "pisco",
            name = "Pisco Sour Catedral",
            category = MenuCategory.CAVA,
            price = 36.00,
            prepTime = "Bar",
            shortDescription = "Pisco Quebranta Gran Selección, limón sutil recién exprimido y amargo de angostura.",
            fullDescription = "El clásico cóctel bandera peruano servido en copa catedral con densa espuma aterciopelada, perfume de amargo de angostura y gotas de jarabe de goma artesanal.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBR3q58VRFzxxgWmNXGveNCcKEjjsSNrBXiA_NkdTcXvaovPwbGzghQuhc5yqn_p6HkE4PTaQjCc4zas3t1rPB0rUq_iz1xktSyfcGz9fslq4KVTwLr08smo6LsyM_heweM9uJjgY0e_9RuuOvSMiNWs4yMuJVhd3A9t_H5xFrGIigC7jm7FyhjpLf1xHkIrTHQah_vcbNycERa4MwzKZra90v4RvV2VTNp1gwLWanyCBne9et1",
            rating = 5.0,
            reviewsCount = 490
        )
    )
}
