/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.java.javamoney.ri.format.spi;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ServiceLoader;

import javax.money.format.ItemFormatException;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

/**
 * Instances of this class can be registered using the {@link ServiceLoader}
 * API. The formatter runtime will ask each registered instance of
 * {@link ItemFormatterFactorySpi} for a formatter given the
 * {@link LocalizationStyle} provided, until an instance will return a non-null
 * instance of {@link AmountFormatter}. This instance finally will be returned
 * to the client.
 * <p>
 * Note that the formatter runtime does not perform any caching of instances
 * returned. It is the responsibility of the implementations of this interface,
 * to implement reuse of resources, where useful. Nevertheless keep in mind that
 * synchronization of shared resources can lead to severe performance issues.
 * Therefore in most of the cases it is reasonable to create a new formatter
 * instance on each access and to delegate caching aspects to the client using
 * this API. Similarly it is not required that the instances returned by the SPI
 * must be thread safe.
 * 
 * @author Anatole Tresch
 */
public interface ItemFormatterFactorySpi<T> {

	/**
	 * Return the target type the owning artifact can be applied to.
	 * 
	 * @return the target type, never {@code null}.
	 */
	public Class<T> getTargetClass();

	/**
	 * Return the style id's supported by this {@link ItemFormatterFactorySpi}
	 * instance.
	 * 
	 * @see LocalizationStyle#getId()
	 * @return the supported style ids, never {@code null}.
	 */
	public Enumeration<String> getSupportedStyleIds();

	/**
	 * Creates a new instance of the formatter defined by the passed
	 * localization style instance, if the style (style id, one of the style's
	 * locales or additional attributes) required are not supported by this
	 * factory, {@code null} should be returned.
	 * 
	 * @see #getTargetClass()
	 * @param style
	 *            the {@link LocalizationStyle} that configures this
	 *            {@link ItemFormatter}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes.
	 * @return a formatter instance representing the given style, or null.
	 * @throws ItemFormatException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemFormatter}.
	 */
	public ItemFormatter<T> getItemFormatter(LocalizationStyle style)
			throws ItemFormatException;

}